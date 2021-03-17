package com.boot.oep.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ccjr
 * @date 2021/3/17 8:47 下午
 */
@Data
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private T data;

    public static <T> ApiResponse<T> ok(){
        return restResult(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), null);
    }
    public static <T> ApiResponse<T> ok(T data){
        return restResult(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), data);
    }
    public static <T> ApiResponse<T> ok(String msg, T data){
        return restResult(ResponseEnum.SUCCESS.getCode(), msg, null);
    }
    public static <T> ApiResponse<T> ok(int code, T data){
        return restResult(code, ResponseEnum.SUCCESS.getMsg(), null);
    }
    public static <T> ApiResponse<T> fail(){
        return restResult(ResponseEnum.FAIL.getCode(), ResponseEnum.FAIL.getMsg(), null);
    }
    public static <T> ApiResponse<T> fail(String msg){
        return restResult(ResponseEnum.FAIL.getCode(), msg, null);
    }
    public static <T> ApiResponse<T> fail(int code, String msg){
        return restResult(code, msg, null);
    }

    private static <T> ApiResponse<T> restResult(int code, String msg ,T data) {
        ApiResponse<T> apiResult = new ApiResponse<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

}
