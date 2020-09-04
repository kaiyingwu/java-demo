package com.example.demo.dao;

import com.example.demo.util.AbstractObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto extends AbstractObject {

    private int id;

    private String studentName;

    private String studentPassword;

    private int state;

    /**
     * 用户token
     */
    private String token;


}
