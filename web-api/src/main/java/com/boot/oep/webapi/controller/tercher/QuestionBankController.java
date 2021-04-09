package com.boot.oep.webapi.controller.tercher;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.boot.oep.model.BaseEntity;
import com.boot.oep.model.Question;
import com.boot.oep.model.QuestionBank;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.controller.BaseController;
import com.boot.oep.webapi.model.dto.BaseQueryDto;
import com.boot.oep.webapi.model.dto.QuesBankQueryDTO;
import com.boot.oep.webapi.model.vo.QuestionBankVo;
import com.boot.oep.webapi.service.QuestionBankService;
import com.boot.oep.webapi.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @PostMapping("/list")
    public ApiResponse<List<QuestionBankVo>> listQuestionBank(@RequestBody QuesBankQueryDTO dto){
        LambdaQueryWrapper<QuestionBank> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionBank::getCreateId, super.getCurId());
        if (dto.getBankName() != null){
            queryWrapper.eq(QuestionBank::getBankName, dto.getBankName());
        }
        if (dto.getStartDate() != null){
            queryWrapper.ge(QuestionBank::getUpdateTime, dto.getStartDate());
        }
        if (dto.getEndDate() != null){
            queryWrapper.le(QuestionBank::getUpdateTime, dto.getEndDate());
        }
        queryWrapper.orderByDesc(BaseEntity::getUpdateTime);
        List<QuestionBankVo> list = questionBankService.list(queryWrapper).stream().map(item->{
          QuestionBankVo questionBankVo = new QuestionBankVo();
          BeanUtils.copyProperties(item, questionBankVo);
          questionBankVo.setQuestionCount(questionService.count(new QueryWrapper<Question>().eq("bank_id", item.getId())));
          return questionBankVo;
        }).collect(Collectors.toList());
        return ApiResponse.ok(list);
    }

    @PostMapping("/listPage")
    public ApiResponse<IPage<QuestionBank>> listQuestionBank(@RequestBody BaseQueryDto dto){
        LambdaQueryWrapper<QuestionBank> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(QuestionBank::getCreateId, super.getCurId());
        queryWrapper.gt(QuestionBank::getQuestionNumber, 0);
        queryWrapper.orderByDesc(BaseEntity::getUpdateTime);
        IPage<QuestionBank> iPage = new Page<>(dto.getCurrent(), dto.getPageSize());
        return ApiResponse.ok(questionBankService.page(iPage, queryWrapper));
    }

    @PostMapping("/add")
    public ApiResponse<QuestionBank> add(@RequestParam("bankName") String bankName){
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
        return ApiResponse.ok(questionBank);
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
