package com.boot.oep.webapi.model.dto;

import lombok.Data;

/**
 * @author zrf
 * @date 2021/4/6
 */
@Data
public class ExamUpdateDto {

    private String id;
    private Integer minutes;
    private Integer peopleNum;
    private Integer state;

}
