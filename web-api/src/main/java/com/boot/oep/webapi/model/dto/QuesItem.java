package com.boot.oep.webapi.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zrf
 * @date 2021/4/6
 */
@Data
public class QuesItem {
    private String questionId;
    private String question;
    private String rightAnswer;
    private Integer type;
    private List<AnswerItem> answerItems;

}
