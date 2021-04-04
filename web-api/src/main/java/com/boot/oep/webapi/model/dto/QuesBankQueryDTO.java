package com.boot.oep.webapi.model.dto;

import lombok.Data;

/**
 * @author ccjr
 * @date 2021/4/4 10:15 下午
 */
@Data
public class QuesBankQueryDTO{

    private String bankName;
    private String startDate;
    private String endDate;

}
