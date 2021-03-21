package com.boot.oep.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ccjr
 * @date 2021/3/21 10:33 上午
 */
public enum UserRoleEnum {
    /**
     * 用户权限
     */
    STUDENT(1, "ROLE_STUDENT"),
    TEACHER(2, "ROLE_TEACHER"),
    ADMIN(3, "ROLE_ADMIN");

    @Getter
    @Setter
    private int roleId;

    @Getter
    @Setter
    private String roleName;

    private UserRoleEnum(int roleId, String roleName){
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public static String getRoleNameById(int userType){
        for (UserRoleEnum item : UserRoleEnum.values()) {
            if(item.roleId == userType){
                return item.roleName;
            }
        }
        return null;
    }



}
