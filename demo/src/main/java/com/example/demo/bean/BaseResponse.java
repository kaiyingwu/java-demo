package com.example.demo.bean;

import com.example.demo.enums.ErrorMsg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
public class BaseResponse<T> {
    /**
     * 请求id
     */
    private String requestId;

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 携带的信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(String msg,int code){
        this.code = code;
        this.message = msg;
    }

    public BaseResponse(int code,String msg){
        this.code = code;
        this.message = msg;
    }

    public BaseResponse(String requestId, int code, String message, T data) {
        this.requestId = requestId;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BaseResponse createOK(String requestId) {
        BaseResponse response = new BaseResponse<>();
        response.code = 0;
        response.message = "";
        response.requestId = requestId;
        return response;
    }

    public static BaseResponse createError(String message, int code) {
        BaseResponse response = new BaseResponse<>();
        response.code = code;
        response.message = message;
        response.requestId = UUID.randomUUID().toString();
        return response;
    }

    public static BaseResponse createError(ErrorMsg errorMsg) {
        BaseResponse response = new BaseResponse<>();
        response.code = errorMsg.getCode();
        response.message = errorMsg.getMsg();
        response.requestId = UUID.randomUUID().toString();
        return response;
    }

    public static <T> BaseResponse<T> createResponse(T data) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = 0;
        response.message = "";
        response.data = data;
        response.requestId = UUID.randomUUID().toString();
        return response;
    }

    public static <T> BaseResponse<T> createResponseAndMsg(T data,String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = 0;
        response.message = message;
        response.data = data;
        response.requestId = UUID.randomUUID().toString();
        return response;
    }

    public static <T> BaseResponse<T> createResponse(T data,String requestId) {
        BaseResponse<T> response = new BaseResponse<>();
        response.code = 0;
        response.message = "";
        response.data = data;
        response.requestId = requestId;
        return response;
    }

    public static BaseResponse createBasicOK() {
        Map<String, String> ok = new HashMap<>();
        ok.put("code", "ok");

        return createResponse(ok);
    }

}
