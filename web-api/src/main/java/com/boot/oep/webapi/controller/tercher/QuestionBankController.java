package com.boot.oep.webapi.controller.tercher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.oep.model.Question;
import com.boot.oep.model.QuestionBank;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.service.QuestionBankService;
import com.boot.oep.webapi.service.QuestionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ccjr
 * @date 2021/3/21 9:24 上午
 */
@RestController
@RequestMapping("/teacher/questionBank")
public class QuestionBankController {

    private final QuestionService questionService;
    private final QuestionBankService questionBankService;


    public QuestionBankController(QuestionBankService questionBankService, QuestionService questionService) {
        this.questionBankService = questionBankService;
        this.questionService = questionService;
    }

    @PostMapping("/add")
    public ApiResponse<String> add(@RequestParam("bankName") String bankName){
        //这里应该做一下处理，当前用户下的题库名不能重复
        QuestionBank questionBank = new QuestionBank();
        questionBank.setBankName(bankName);
        questionBankService.save(questionBank);
        return ApiResponse.ok();
    }

    @PostMapping("/remove")
    @Transactional(rollbackFor = {Exception.class})
    public ApiResponse<String> del(@RequestParam("bankIds") List<String> bankIds){
        //删除该题库下的所有问题
        questionService.remove(new QueryWrapper<Question>().in("bank_id", bankIds));
        questionBankService.removeByIds(bankIds);
        return ApiResponse.ok();
    }

    @PostMapping("/update")
    public ApiResponse<String> update(@RequestParam("id") String id, @RequestParam("bankName") String bankName){
        //这里应该做一下处理，当前用户下的题库名不能重复
        QuestionBank questionBank = new QuestionBank();
        questionBank.setId(id);
        questionBank.setBankName(bankName);
        questionBankService.updateById(questionBank);
        return ApiResponse.ok();
    }

}
