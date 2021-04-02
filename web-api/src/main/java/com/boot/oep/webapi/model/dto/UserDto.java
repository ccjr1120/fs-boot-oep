package com.boot.oep.webapi.model.dto;

import lombok.Data;

/**
 * @author zrf
 * @date 2021/4/2
 */
@Data
public class UserDto {

    private String id;
    private String name;
    private String username;
    private String password;
    private Integer roleId;

}
