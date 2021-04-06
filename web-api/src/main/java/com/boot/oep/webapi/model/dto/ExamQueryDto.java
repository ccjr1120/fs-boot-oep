package com.boot.oep.webapi.model.dto;

import lombok.Data;

/**
 * @author ccjr
 * @date 2021/4/5 5:20 下午
 */
@Data
public class ExamQueryDto extends BaseQueryDto{

    private String name;
    private String startDate;
    private String endDate;
    private Integer type;

}
