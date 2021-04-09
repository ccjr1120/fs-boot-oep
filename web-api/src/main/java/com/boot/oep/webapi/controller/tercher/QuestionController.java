package com.boot.oep.webapi.controller.tercher;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.boot.oep.model.Question;
import com.boot.oep.model.QuestionBank;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.excel.UploadQuestionListener;
import com.boot.oep.webapi.model.dto.QuestionDto;
import com.boot.oep.webapi.service.QuestionBankService;
import com.boot.oep.webapi.service.QuestionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author ccjr
 * @date 2021/3/21 9:14 上午
 */

@RestController
@RequestMapping("/teacher/question")
public class QuestionController {

    private final QuestionService questionService;
    @Resource
    private QuestionBankService questionBankService;

    public QuestionController(QuestionService questionService, QuestionBankService questionBankService) {
        this.questionService = questionService;
    }

    @PostMapping("/list")
    public ApiResponse<List<Question>> listQuestionByBankId(@RequestParam String bankId){
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getBankId, bankId);
        return ApiResponse.ok(questionService.list(queryWrapper));
    }

    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<String> addOne(@RequestBody QuestionDto dto){
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getBankId, dto.getBankId());
        queryWrapper.eq(Question::getTitle, dto.getTitle());
        Question question = questionService.getOne(queryWrapper);
        if (question != null){
            return ApiResponse.fail("同一题库下不能出现标题重复的问题");
        }
        question = new Question();
        BeanUtil.copyProperties(dto, question);
        questionService.save(question);
        QuestionBank questionBank = questionBankService.getById(dto.getBankId());
        questionBank.setQuestionNumber(questionBank.getQuestionNumber() + 1);
        questionBankService.updateById(questionBank);
        return ApiResponse.ok();
    }

    @PostMapping("/excel")
    public void excel(@RequestParam("file") MultipartFile file, @RequestParam("bankId") String bankId,  HttpServletResponse response) throws IOException {
        EasyExcel.read(file.getInputStream(), new UploadQuestionListener(response, questionService, questionBankService, bankId)).sheet().doRead();
    }

    @PostMapping("/update")
    public ApiResponse<String> importMore(@RequestBody QuestionDto dto){
        Question question = new Question();
        BeanUtil.copyProperties(dto, question);
        questionService.updateById(question);
        return ApiResponse.ok();
    }

    @PostMapping("/del")
    public ApiResponse<String> getPaperService(@RequestParam("id") List<String> questionIdList) {
        questionService.removeByIds(questionIdList);
        return ApiResponse.ok();
    }

}
