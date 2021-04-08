package com.boot.oep.webapi.controller;

import com.boot.oep.security.SecurityUserUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ccjr
 * @date 2021/4/2 8:42 下午
 */
public class BaseController {

    public static final String ROOT_KEY = "/";


    public String getCurId(){
        return SecurityUserUtils.getCurUser().getId();
    }

}
