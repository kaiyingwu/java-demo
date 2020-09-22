package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.bean.BaseResponse;
import com.example.demo.dao.StudentDo;

import java.util.List;


/**
 * Create by wky on 2020/9/21
 **/

public interface MyBatisPlusDemoService extends IService<StudentDo> {

    BaseResponse getTwoTableMsg();

    BaseResponse saveOne(StudentDo studentDo);

    BaseResponse saveList(List<StudentDo> studentDos);

}
