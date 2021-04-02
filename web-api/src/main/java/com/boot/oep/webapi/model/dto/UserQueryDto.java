package com.boot.oep.webapi.model.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zrf
 * @date 2021/4/2
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDto extends BaseQueryDto{

    private String queryStr;

}
