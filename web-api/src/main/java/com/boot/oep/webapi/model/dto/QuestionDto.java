package com.boot.oep.webapi.model.dto;

import lombok.Data;

/**
 * @author ccjr
 * @date 2021/3/21 9:19 上午
 */
@Data
public class QuestionDto {
    private String id;

    private String bankId;
    private String question;
    private String rightAnswer;

    /**
     * 错误答案Json存储
     */
    private String wrongAnswer;

}
