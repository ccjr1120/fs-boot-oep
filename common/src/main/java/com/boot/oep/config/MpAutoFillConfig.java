package com.boot.oep.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateId",request.getSession().getAttribute("user"),metaObject);
        this.setFieldValByName("createId",request.getSession().getAttribute("user"),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("updateId",request.getSession().getAttribute("user"),metaObject);
    }

}
