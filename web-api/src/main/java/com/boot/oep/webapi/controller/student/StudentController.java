package com.boot.oep.webapi.controller.student;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.oep.model.BaseEntity;
import com.boot.oep.model.Exam;
import com.boot.oep.model.ExamRecord;
import com.boot.oep.model.QuestionBank;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.controller.BaseController;
import com.boot.oep.webapi.model.dto.BaseQueryDto;
import com.boot.oep.webapi.model.dto.QuesItem;
import com.boot.oep.webapi.model.vo.MyExamVo;
import com.boot.oep.webapi.service.ExamRecordService;
import com.boot.oep.webapi.service.ExamService;
import com.boot.oep.webapi.service.QuestionBankService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author ccjr
 * @date 2021/3/18 9:06 下午
 */
@RestController
@RequestMapping("/student")
public class StudentController extends BaseController {

    @Resource
    private ExamRecordService examRecordService;
    @Resource
    private QuestionBankService questionBankService;
    @Resource
    private ExamService examService;

    @PostMapping("/myExam")
    public ApiResponse<IPage<MyExamVo>> myExam(@RequestBody BaseQueryDto baseQueryDto){
        IPage<ExamRecord> iPage = new Page<>(baseQueryDto.getCurrent(), baseQueryDto.getPageSize());
        iPage = examRecordService.page(iPage, new LambdaQueryWrapper<ExamRecord>()
                .eq(BaseEntity::getCreateId, getCurId())
                .eq(ExamRecord::getState, 1));
        return ApiResponse.ok(iPage.convert(item->{
            MyExamVo myExamVo = new MyExamVo();
            myExamVo.setId(item.getId());
            Exam exam = examService.getById(item.getBankId());
            myExamVo.setExamName(exam.getName());
            myExamVo.setGrade(item.getExamResult());
            myExamVo.setCreateTime(item.getCreateTime());
            myExamVo.setUpdateTime(item.getUpdateTime());
            List<QuesItem> quesItems = JSON.parseObject(item.getOptionList(), new TypeReference<List<QuesItem>>(){});
            Map<String, List<String>> answerMap = JSON.parseObject(item.getAnswers(), new TypeReference<Map<String, List<String>>>(){});
            int wrongSum = 0;
            for (QuesItem quesItem : quesItems) {
                String right = quesItem.getRightAnswer();
                if (answerMap == null){
                    wrongSum = quesItems.size();
                    break;
                }
                List<String> answers = answerMap.get(quesItem.getQuestionId());
                if (answers == null){
                    wrongSum++;
                    continue;
                }
                for (String answer : answers) {
                    if (!right.contains(answer)) {
                        wrongSum++;
                        break;
                    }
                }
            }
            myExamVo.setQuestionNum(quesItems.size());
            myExamVo.setWrongNum(wrongSum);
            return myExamVo;
        }));
    }

}
