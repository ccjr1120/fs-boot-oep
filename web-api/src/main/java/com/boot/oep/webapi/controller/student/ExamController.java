package com.boot.oep.webapi.controller.student;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.oep.model.BaseEntity;
import com.boot.oep.model.Exam;
import com.boot.oep.model.ExamRecord;
import com.boot.oep.model.Question;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.controller.BaseController;
import com.boot.oep.webapi.model.dto.AnswerItem;
import com.boot.oep.webapi.model.dto.QuesItem;
import com.boot.oep.webapi.model.vo.QuesItemVo;
import com.boot.oep.webapi.service.ExamRecordService;
import com.boot.oep.webapi.service.ExamService;
import com.boot.oep.webapi.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author zrf
 * @date 2021/4/6
 */
@RestController
@RequestMapping("/student/exam")
public class ExamController extends BaseController {

    @Resource
    private ExamService examService;
    @Resource
    private QuestionService questionService;
    @Resource
    private ExamRecordService examRecordService;

    @PostMapping("/start")
    public ApiResponse<List<QuesItemVo>> startExam(@RequestParam("key") String key){
        Exam exam = examService.getOne(new QueryWrapper<Exam>().eq("random_str", key));
        if (exam == null){
            return ApiResponse.fail("口令无效");
        }
        if (exam.getState() == 1){
            return ApiResponse.fail("该考试已结束");
        }
        ExamRecord examRecord = new ExamRecord();
        examRecord.setBankId(exam.getId());
        List<String> qIdList;
        if (exam.getIsRandom() == 1){
            LambdaQueryWrapper<Question> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(Question::getBankId, exam.getSourceIds());
            lambdaQueryWrapper.last(" ORDER BY RAND() LIMIT " + exam.getQuestionNum() + ";");
            List<Question> questions = questionService.list(lambdaQueryWrapper);
            qIdList = questions.stream().map(Question::getId).collect(Collectors.toList());
        }else{
           qIdList = JSON.parseArray(exam.getSourceIds(), String.class);
        }
        List<QuesItem> quesItems = new ArrayList<>();
        qIdList.forEach(item->{
            QuesItem quesItem= new QuesItem();
            Question question= questionService.getById(item);
            quesItem.setType(question.getType());
            quesItem.setQuestion(question.getTitle());
            List<AnswerItem> answerItems =new ArrayList<>();
            List<String> rights = JSON.parseArray(question.getRightAnswer(), String.class);
            StringBuilder rightStr = new StringBuilder();
            List<String> wrongs = JSON.parseArray(question.getWrongAnswer(), String.class);
            List<String>  list = new ArrayList<>(5);
            int size = rights.size() + wrongs.size();
            for (int i = 1; i <= size; i++) {
                list.add(String.valueOf((char)(64+i)));
            }
            for (String right : rights) {
                AnswerItem answerItem = new AnswerItem();
                String label = this.getAnswerLabel(list);
                answerItem.setContent(right);
                answerItem.setLabel(label);
                rightStr.append(label);
                answerItems.add(answerItem);
            }
            quesItem.setRightAnswer(rightStr.toString());
            for (String wrong : wrongs) {
                AnswerItem answerItem = new AnswerItem();
                String label = this.getAnswerLabel(list);
                answerItem.setContent(wrong);
                answerItem.setLabel(label);
                answerItems.add(answerItem);
            }
            answerItems.sort(new Comparator<AnswerItem>() {
                @Override
                public int compare(AnswerItem o1, AnswerItem o2) {
                    return o1.getLabel().compareTo(o2.getLabel());
                }
            });
            quesItem.setAnswerItems(answerItems);
            quesItems.add(quesItem);
        });
        examRecord.setOptionList(JSON.toJSONString(quesItems));
        examRecordService.save(examRecord);
        List<QuesItemVo> quesItemVos = new ArrayList<QuesItemVo>();
        quesItems.forEach(item->{
            QuesItemVo quesItemVo = new QuesItemVo();
            BeanUtils.copyProperties(item, quesItemVo);
            quesItemVos.add(quesItemVo);
        });
        return ApiResponse.ok(quesItemVos);
    }

    @PostMapping("/checkStart")
    public ApiResponse<String> checkStart(){
        ExamRecord examRecord = examRecordService.getOne(new LambdaQueryWrapper<ExamRecord>()
        .eq(BaseEntity::getCreateId, getCurId())
        .eq(ExamRecord::getState, "0"));
        if (examRecord == null){
            return ApiResponse.ok();
        }
        return ApiResponse.ok(examRecord.getId());
    }

    private String getAnswerLabel(List<String> list){
        Random random = new Random();
        int n = random.nextInt(list.size());
        return list.remove(n);
    }

}
