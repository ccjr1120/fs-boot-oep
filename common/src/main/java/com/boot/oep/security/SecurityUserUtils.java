package com.boot.oep.security;

import com.boot.oep.model.SysUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author ccjr
 * @date 2021/3/30 10:11 下午
 */
@Component
public class SecurityUserUtils {

    public LoginUserDetails getCurUser() {
        LoginUserDetails loginUserDetails = (LoginUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (loginUserDetails == null){
            //未登录测试用
            return new LoginUserDetails();
        }
        return loginUserDetails;
    }

}
