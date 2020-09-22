package com.example.demo.enums;

import lombok.Getter;

/**
 * Create by wky on 2020/9/21
 **/

public enum  ErrorMsg {

    INNER(-10001,"系统错误"),
    STUDENT_NAME_EMPTY(-10002,"参数异常"),
    STUDENT_NAME_HAVE(-10003,"学生用户名已经存在");

    Integer code;
    String msg;

    ErrorMsg(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public Integer getCode(){
        return code;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getMsg(){
        return msg;
    }

    public static ErrorMsg getByCode(Integer code) {
        for (ErrorMsg typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return ErrorMsg.INNER;
    }

    public static ErrorMsg getByMsg(String Msg) {
        for (ErrorMsg typeEnum : values()) {
            if (typeEnum.getMsg().equals(Msg)) {
                return typeEnum;
            }
        }
        return ErrorMsg.INNER;
    }

}
