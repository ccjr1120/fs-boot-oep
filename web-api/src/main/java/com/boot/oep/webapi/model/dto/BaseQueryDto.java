package com.boot.oep.webapi.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author ccjr
 * @date 2021/3/28 10:06 下午
 */
@Data
public class BaseQueryDto {

    @NotNull(message = "页数不能为空")
    private Integer current;
    @NotNull(message = "页大小不能为空")
    private Integer size;
}
