package com.boot.oep.webapi.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.boot.oep.mapper.QuestionMapper;
import com.boot.oep.model.Question;
import com.boot.oep.model.QuestionBank;
import com.boot.oep.result.ApiResponse;
import com.boot.oep.webapi.model.vo.ImportResultVo;
import com.boot.oep.webapi.service.QuestionBankService;
import com.boot.oep.webapi.service.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 使用EasyExcel读取上次的问题的监听器
 * @author ccjr
 * @date 2021/3/20 11:55 下午
 */
public class UploadQuestionListener extends AnalysisEventListener<Map<Integer, String>> {

    private static final int BATCH_COUNT = 3;
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
    private int count = 0;

    private final HttpServletResponse resp;
    private QuestionService questionService;
    private QuestionBankService questionBankService;
    private String bankId;
    private ImportResultVo importResultVo = new ImportResultVo();
    private List<ImportResultVo.FailItem> failItemList = new ArrayList<>();

    public UploadQuestionListener(HttpServletResponse resp, QuestionService questionService, QuestionBankService questionBankService, String bankId) {
        this.resp = resp;
        this.questionService = questionService;
        this.questionBankService = questionBankService;
        this.bankId = bankId;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        list.add(data);
        if (list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @SneakyThrows
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        resp.setHeader("Content-Type", "application/json;charset=utf-8");
        resp.getWriter().print(new ObjectMapper().writeValueAsString(ApiResponse.ok(importResultVo)));
        resp.getWriter().flush();
    }



    private void saveData() {
        if (!list.isEmpty() && list.size() == BATCH_COUNT){
            count++;
            String title = list.get(0).get(0);
            Question question = questionService.getOne(new LambdaQueryWrapper<Question>().eq(Question::getTitle, title).eq(Question::getBankId, bankId));
            if (question == null){
                question = new Question();
                question.setTitle(title);
                question.setBankId(bankId);
                Collection<String> valueCollection = list.get(1).values();
                if (valueCollection.isEmpty()){
                    ImportResultVo.FailItem failItem = new ImportResultVo.FailItem();
                    failItem.setCol(count * 3);
                    failItem.setReason("正确答案不能为空");
                    if (importResultVo.getFailItemList() == null){
                        importResultVo.setFailItemList(new ArrayList<>());
                    }
                    importResultVo.getFailItemList().add(failItem);
                    return;
                }
                List<String> rightList = new ArrayList<String>(valueCollection);
                if (rightList.size() >= 5){
                    question.setRightAnswer(JSON.toJSONString(rightList.subList(0, 5)));
                }else{
                    question.setRightAnswer(JSON.toJSONString(rightList));
                    Collection<String> valueCollection1 = list.get(2).values();
                    List<String> wrongList = new ArrayList<String>(valueCollection1);
                    if (wrongList.size() + rightList.size() > 5){
                        question.setWrongAnswer(JSON.toJSONString(wrongList.subList(0, 5-rightList.size())));
                    }else{
                        question.setWrongAnswer(JSON.toJSONString(wrongList));
                    }
                }
                questionService.save(question);
                QuestionBank questionBank = questionBankService.getById(bankId);
                questionBank.setQuestionNumber(questionBank.getQuestionNumber() + 1);
                questionBankService.updateById(questionBank);
                importResultVo.setSuccessNum(importResultVo.getSuccessNum() + 1);
            }else{
                ImportResultVo.FailItem failItem = new ImportResultVo.FailItem();
                failItem.setCol(count * 3);
                failItem.setReason("题目已存在");
                if (importResultVo.getFailItemList() == null){
                    importResultVo.setFailItemList(new ArrayList<>());
                }
                importResultVo.getFailItemList().add(failItem);
            }
        }
    }

}
