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

    private String title;
    private Integer type;
    private String rightAnswer;
    private String wrongAnswer;

}
