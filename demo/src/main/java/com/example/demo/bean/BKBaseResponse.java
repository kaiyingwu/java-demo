package com.example.demo.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@ToString
public class BKBaseResponse<T> {

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

    public BKBaseResponse() {
    }

    public BKBaseResponse(String msg,int code){
        this.code = code;
        this.message = msg;
    }

    public BKBaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static BKBaseResponse createOK() {
        BKBaseResponse response = new BKBaseResponse<>();
        response.code = 0;
        response.message = "";
        return response;
    }

    public static BKBaseResponse createError(String message, int code) {
        BKBaseResponse response = new BKBaseResponse<>();
        response.code = code;
        response.message = message;
        return response;
    }

    public static <T> BKBaseResponse<T> createResponse(T data) {
        BKBaseResponse<T> response = new BKBaseResponse<>();
        response.code = 0;
        response.message = "";
        response.data = data;
        return response;
    }

    public static <T> BKBaseResponse<T> createResponseAndMsg(T data,String message) {
        BKBaseResponse<T> response = new BKBaseResponse<>();
        response.code = 0;
        response.message = message;
        response.data = data;
        return response;
    }


    public static BKBaseResponse createBasicOK() {
        Map<String, String> ok = new HashMap<>();
        ok.put("code", "ok");

        return createResponse(ok);
    }


}
