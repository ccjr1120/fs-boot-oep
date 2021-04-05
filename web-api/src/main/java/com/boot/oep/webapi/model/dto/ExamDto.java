package com.boot.oep.webapi.model.dto;

import lombok.Data;

import java.util.List;

/**
 * @author ccjr
 * @date 2021/4/5 9:07 下午
 */
@Data
public class ExamDto {
    private String randomStr;
    private String name;
    private Integer minutes;
    private Integer peopleNum;
    private Integer questionNum;
    private Integer isRandom;
    private List<String> bankIds;
}
