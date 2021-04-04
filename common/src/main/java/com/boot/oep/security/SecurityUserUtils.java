package com.boot.oep.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author ccjr
 * @date 2021/3/30 10:11 下午
 */
public class SecurityUserUtils {

    public static LoginUserDetails getCurUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)){
            //未登录测试用
            LoginUserDetails loginUserDetails = new LoginUserDetails();
            loginUserDetails.setId("un_login");
            return loginUserDetails;
        }
        return (LoginUserDetails) principal;
    }

}
