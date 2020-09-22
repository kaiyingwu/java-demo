package com.example.demo.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.enums.ErrorMsg;
import com.example.demo.util.AbstractObject;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

@Getter
@Setter
@TableName("student")
public class StudentDo extends AbstractObject {

    private int id;

    @NotBlank(message =  "账号不能为空")
    private String studentName;

    @NotBlank(message = "密码不能为空")
    private String studentPassword;

    private int state;

}
