package com.boot.oep.webapi.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ccjr
 * @date 2021/3/28 10:09 下午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuQueryDto extends BaseQueryDto{
    private String queryStr;
}
