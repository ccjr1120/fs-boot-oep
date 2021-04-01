package com.boot.oep.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.boot.oep.security.SecurityUserUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * mybatis-plus 字段自动填充
 * @author ccjr
 * @date 2021/3/17 8:55 下午
 */
@Component
public class MpAutoFillConfig implements MetaObjectHandler {
    @Resource
    private HttpServletRequest request;
    @Resource
    private SecurityUserUtils userUtils;

    @Override
    public void insertFill(MetaObject metaObject) {
        String userId = "unLogin";
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateId", userId, metaObject);
        this.setFieldValByName("createId", userId, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        String userId = "unLogin";
        this.setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
        this.setFieldValByName("updateId", userId, metaObject);
    }

}
