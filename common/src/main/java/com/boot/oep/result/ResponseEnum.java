package com.boot.oep.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 系统响应码枚举类
 * @author ccjr
 * @date 2021/3/17 9:15 下午
 */
public enum ResponseEnum {
    /**
     * 成功与失败
     */
    SUCCESS(0, "操作成功"),
    FAIL(1, "操作失败"),
    LOGIN_FAIL(100, "登录失败"),
    NO_ROLE(101, "无访问权限"),
    UNKNOWN(500, "系统错误");
    private int code;
    private String msg;

    ResponseEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode(){
        return this.code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
