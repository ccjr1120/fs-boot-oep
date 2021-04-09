package com.boot.oep.webapi.controller.tercher;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.oep.model.*;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.service.SysUserService;
import com.boot.oep.webapi.controller.BaseController;
import com.boot.oep.webapi.model.dto.*;
import com.boot.oep.webapi.model.vo.MyExamVo;
import com.boot.oep.webapi.service.ExamRecordService;
import com.boot.oep.webapi.service.ExamService;
import com.boot.oep.webapi.service.QuestionBankService;
import com.boot.oep.webapi.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ccjr
 * @date 2021/4/5 4:39 下午
 */
@RestController
@RequestMapping("/teacher/exam")
public class ExamManageController extends BaseController {

    @Resource
    private ExamService examService;
    @Resource
    private QuestionBankService questionBankService;
    @Resource
    private QuestionService questionService;
    @Resource
    private ExamRecordService examRecordService;
    @Resource
    private SysUserService sysUserService;

    @PostMapping("/list")
    public ApiResponse<IPage<Exam>> listExamVo(@RequestBody ExamQueryDto queryDto){
        IPage<Exam> iPage = new Page<>(queryDto.getCurrent(), queryDto.getPageSize());
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<>();
        if (queryDto.getName() != null){
            wrapper.like(Exam::getName, queryDto.getName());
        }
        if (queryDto.getType() != null){
            wrapper.eq(Exam::getState, queryDto.getType());
        }
        if (queryDto.getStartDate() != null){
            wrapper.ge(Exam::getCreateTime, queryDto.getStartDate());
        }
        if (queryDto.getEndDate() != null){
            wrapper.le(Exam::getCreateTime, queryDto.getEndDate());
        }
        iPage = examService.page(iPage, wrapper);
        return ApiResponse.ok(iPage);
    }

    @PostMapping("/add")
    public ApiResponse<IPage<Exam>> add(@RequestBody ExamDto examDto){
        int count = questionService.count(new QueryWrapper<Question>().in("bank_id", examDto.getBankIds()));
        if (count < examDto.getQuestionNum()){
            return ApiResponse.fail("所选题库没有足够多的题目");
        }
        Exam exam = examService.getOne(new QueryWrapper<Exam>()
                .eq("random_str", examDto.getRandomStr())
                .eq("state", 0));
        if (exam != null){
            return ApiResponse.fail("该口令已存在");
        }
        exam = new Exam();
        BeanUtils.copyProperties(examDto, exam);
        if (examDto.getIsRandom() == 0) {
            LambdaQueryWrapper<Question> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.in(Question::getBankId, examDto.getBankIds());
            lambdaQueryWrapper.last(" ORDER BY RAND() LIMIT " + examDto.getQuestionNum() + ";");
            List<Question> questions = questionService.list(lambdaQueryWrapper);
            List<String> qIdList = questions.stream().map(Question::getId).collect(Collectors.toList());
            exam.setSourceIds(JSON.toJSONString(qIdList));
        }else{
            exam.setSourceIds(JSON.toJSONString(examDto.getBankIds()));
        }
        examService.save(exam);
        return ApiResponse.ok();
    }

    @PostMapping("/update")
    public ApiResponse<String> update(@RequestBody ExamUpdateDto dto){
        Exam exam =new Exam();
        BeanUtils.copyProperties(dto, exam);
        examService.updateById(exam);
        return ApiResponse.ok();
    }

    @PostMapping("/listRecord")
    public ApiResponse<IPage<MyExamVo>> listRecord(@RequestBody ExamRecordDto recordDto){
        IPage<ExamRecord> iPage = new Page<>(recordDto.getCurrent(), recordDto.getPageSize());
        iPage = examRecordService.page(iPage, new LambdaQueryWrapper<ExamRecord>()
                .eq(ExamRecord::getBankId, recordDto.getExamId())
                .eq(ExamRecord::getState, 1));
        return ApiResponse.ok(iPage.convert(item->{
            SysUser sysUser = sysUserService.getById(item.getCreateId());
            MyExamVo myExamVo = new MyExamVo();
            myExamVo.setId(item.getId());
            Exam exam = examService.getById(item.getBankId());
            myExamVo.setSysUsername(sysUser.getUsername());
            myExamVo.setExamName(exam.getName());
            myExamVo.setGrade(item.getExamResult());
            myExamVo.setCreateTime(item.getCreateTime());
            myExamVo.setUpdateTime(item.getUpdateTime());
            myExamVo.setQuestionNum(exam.getQuestionNum());
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
