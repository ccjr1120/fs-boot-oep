package com.boot.oep.webapi.controller.tercher;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.oep.model.Question;
import com.boot.oep.model.QuestionBank;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.controller.BaseController;
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
public class QuestionBankController extends BaseController {

    private final QuestionService questionService;
    private final QuestionBankService questionBankService;


    public QuestionBankController(QuestionBankService questionBankService, QuestionService questionService) {
        this.questionBankService = questionBankService;
        this.questionService = questionService;
    }

    @PostMapping("/add")
    public ApiResponse<String> add(@RequestParam("bankName") String bankName){
        if (bankName == null){
            return ApiResponse.ok();
        }
        QuestionBank questionBank = questionBankService.getOne(new QueryWrapper<QuestionBank>()
                .eq("bank_name", bankName).eq("create_id", super.getCurId()));
        if (questionBank != null){
            return ApiResponse.fail("保存失败，该名称已存在");
        }
        questionBank = new QuestionBank();
        questionBank.setBankName(bankName);
        questionBankService.save(questionBank);
        return ApiResponse.ok(questionBank.getId());
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
    public ApiResponse<QuestionBank> update(@RequestParam("id") String id, @RequestParam("bankName") String bankName){
        QuestionBank questionBank = questionBankService.getOne(new QueryWrapper<QuestionBank>()
                .eq("bank_name", bankName).eq("create_id", super.getCurId()));
        if (questionBank != null && !bankName.equals(questionBank.getBankName())){
            return ApiResponse.fail("保存失败，该名称已存在");
        }
        questionBank = new QuestionBank();
        questionBank.setId(id);
        questionBank.setBankName(bankName);
        questionBankService.updateById(questionBank);
        return ApiResponse.ok(questionBank);
    }

}
