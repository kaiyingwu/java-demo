package com.example.demo.enums;

import com.example.demo.interfaces.CodeEnum;

public enum  UserQualificationVerifierStatus implements CodeEnum {

    Failed(-1), UnSubmit(0) , Pass(1) ;

    public final int code ;

    UserQualificationVerifierStatus(int code) {
        this.code = code;
    }

    @Override
    public int getCode(){
        return code ;
    }
}
