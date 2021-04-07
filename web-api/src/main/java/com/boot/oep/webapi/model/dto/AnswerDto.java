package com.boot.oep.webapi.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author zrf
 * @date 2021/4/7
 */
@Data
public class AnswerDto {

    private String id;
    private List<String> answer;

}
