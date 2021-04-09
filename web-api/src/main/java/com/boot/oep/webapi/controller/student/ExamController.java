package com.boot.oep.webapi.controller.student;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.oep.model.*;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.controller.BaseController;
import com.boot.oep.webapi.model.dto.AnswerDto;
import com.boot.oep.webapi.model.dto.AnswerItem;
import com.boot.oep.webapi.model.dto.QuesItem;
import com.boot.oep.webapi.model.vo.QuesItemVo;
import com.boot.oep.webapi.service.ExamRecordService;
import com.boot.oep.webapi.service.ExamService;
import com.boot.oep.webapi.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;
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
            quesItem.setQuestionId(item);
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
                String value = this.getAnswerLabel(list);
                answerItem.setValue(value);
                answerItem.setLabel(value + ". " + right);
                rightStr.append(value);
                answerItems.add(answerItem);
            }
            quesItem.setRightAnswer(rightStr.toString());
            for (String wrong : wrongs) {
                AnswerItem answerItem = new AnswerItem();
                String value = this.getAnswerLabel(list);
                answerItem.setValue(value);
                answerItem.setLabel(value + ". " + wrong);
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

    @PostMapping("/continue")
    public ApiResponse<List<QuesItemVo>> continueExam(){
        ExamRecord examRecord = examRecordService.getOne(new LambdaQueryWrapper<ExamRecord>()
                .eq(BaseEntity::getCreateId, getCurId())
                .eq(ExamRecord::getState, "0"));
        if (examRecord == null){
            return ApiResponse.fail("获取失败");
        }
        List<QuesItemVo> quesItemVos = JSON.parseObject(examRecord.getOptionList(), new TypeReference<List<QuesItemVo>>(){});
        if (StrUtil.isNotBlank(examRecord.getAnswers())){
            Map<String, List<String>> answerMap = JSON.parseObject(examRecord.getAnswers(), new TypeReference<HashMap<String, List<String>>>() {
            });
            quesItemVos.forEach(quesItemVo -> {
                quesItemVo.setMyAnswer(answerMap.get(quesItemVo.getQuestionId()));
            });
        }
        return ApiResponse.ok(quesItemVos);
    }

    @PostMapping("/saveAnswer")
    public ApiResponse<String> saveAnswer(@RequestBody AnswerDto dto){
        ExamRecord examRecord = getMyExam();
        Map<String, List<String>> answerMap;
        if (StrUtil.isNotBlank(examRecord.getAnswers())){
            answerMap = JSON.parseObject(examRecord.getAnswers(), new TypeReference<HashMap<String, List<String>>>(){});
        }else{
            answerMap = new HashMap<>(1);
        }
        answerMap.put(dto.getId(), dto.getAnswer());
        examRecord.setAnswers(JSON.toJSONString(answerMap));
        examRecordService.updateById(examRecord);
        return ApiResponse.ok();
    }

    @PostMapping("/done")
    public ApiResponse<Integer> doneAnswer(){
        ExamRecord examRecord = getMyExam();
        examRecord.setState(1);
        examRecordService.updateById(examRecord);
        Map<String, List<String>> answerMap = JSON.parseObject(examRecord.getAnswers(), new TypeReference<Map<String, List<String>>>(){});
        List<QuesItem> quesItems = JSON.parseObject(examRecord.getOptionList(), new TypeReference<List<QuesItem>>(){});
        int sum = 0;
        for (QuesItem quesItem : quesItems) {
            boolean flag = true;
            String right = quesItem.getRightAnswer();
            List<String> answers = answerMap.get(quesItem.getQuestionId());
            if (answers == null){
                continue;
            }
            for (String answer : answers) {
                if (!right.contains(answer)) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                sum++;
            }
        }
        examRecord.setExamResult(100/quesItems.size()*sum);
        examRecordService.updateById(examRecord);
        return ApiResponse.ok(examRecord.getExamResult());
    }

    @PostMapping("/clearAnswer")
    public ApiResponse<String> saveAnswer(@RequestParam("id") String id){
        ExamRecord examRecord = getMyExam();
        Map<String, List<String>> answerMap = JSON.parseObject(examRecord.getAnswers(), new TypeReference<HashMap<String, List<String>>>(){});
        answerMap.remove(id);
        examRecord.setAnswers(JSON.toJSONString(answerMap));
        examRecordService.updateById(examRecord);
        return ApiResponse.ok();
    }

    private ExamRecord getMyExam(){
        LambdaQueryWrapper<ExamRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BaseEntity::getCreateId, getCurId())
                .eq(ExamRecord::getState, 0);
        return examRecordService.getOne(queryWrapper);
    }

    private String getAnswerLabel(List<String> list){
        Random random = new Random();
        int n = random.nextInt(list.size());
        return list.remove(n);
    }

}
