package com.example.demo.dao;




import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class TeacherDo {

    @NotNull(message = "id不能为空")
    @Min(value = 1,message = "id不能小于1")
    private int id;
    private String teacherName;
    private String teacherPassword;

    @NotNull(message = "state不能为空")
    private String state;

}
