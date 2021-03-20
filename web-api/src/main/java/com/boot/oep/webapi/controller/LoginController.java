package com.boot.oep.webapi.controller;

import com.boot.oep.result.ApiResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ccjr
 * @date 2021/3/17 8:45 下午
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/login")
    public ApiResponse<String> login(){
        return ApiResponse.ok();
    }

    @RequestMapping("/logout")
    public ApiResponse<String> logout(){
        return ApiResponse.ok();
    }


}
