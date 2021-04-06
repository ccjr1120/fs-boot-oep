package com.boot.oep.webapi.controller.tercher;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.oep.model.Exam;
import com.boot.oep.model.Question;
import com.boot.oep.model.QuestionBank;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.model.dto.ExamDto;
import com.boot.oep.webapi.model.dto.ExamQueryDto;
import com.boot.oep.webapi.model.dto.ExamUpdateDto;
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
import java.util.stream.Collectors;

/**
 * @author ccjr
 * @date 2021/4/5 4:39 下午
 */
@RestController
@RequestMapping("/teacher/exam")
public class ExamManageController {

    @Resource
    private ExamService examService;
    @Resource
    private QuestionBankService questionBankService;
    @Resource
    private QuestionService questionService;

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
        int count = questionBankService.count(new QueryWrapper<QuestionBank>().in("id", examDto.getBankIds()));
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
        if (examDto.getIsRandom() == 1) {
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
}
