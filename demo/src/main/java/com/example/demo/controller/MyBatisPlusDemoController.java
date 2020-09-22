package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.bean.BaseResponse;
import com.example.demo.dao.StudentDo;
import com.example.demo.enums.ErrorMsg;
import com.example.demo.service.MyBatisPlusDemoService;
import com.example.demo.validList.ValidList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * Create by wky on 2020/9/21
 **/

@RestController
@Slf4j
@RequestMapping("/mp")
public class MyBatisPlusDemoController {

    @Autowired
    MyBatisPlusDemoService myBatisPlusDemoService;

    /**
     * 单数据查询
     * @return
     */
    @GetMapping("/select/one")
    public BaseResponse  mpTestOneSelect(){

        QueryWrapper<StudentDo> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(StudentDo::getId,1);
        //该情况只能用于数据库中唯一数据查询，多数据情况下会返回错误，需要注意
        StudentDo studentDo = myBatisPlusDemoService.getOne(queryWrapper);
        return BaseResponse.createResponse(studentDo);
    }

    /**
     * 多数据查询list
     * @return
     */
    @GetMapping("/select/list")
    public BaseResponse mpTestListSelect(){
        QueryWrapper<StudentDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentDo::getStudentPassword,"123");
        List<StudentDo> list = myBatisPlusDemoService.list(queryWrapper);

        return BaseResponse.createResponse(list);
    }

    /**
     * 数据统计条数
     * @return
     */
    @GetMapping("/select/count")
    public BaseResponse mpTestSelectCount(){
        QueryWrapper<StudentDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(StudentDo::getStudentPassword,"123");
        int count = myBatisPlusDemoService.count(queryWrapper);
        return BaseResponse.createResponse(count);
    }

    /**
     * 单表分页查询
     * @return
     */
    @GetMapping("/select/page")
    public BaseResponse mpTestSelectPage(){
        QueryWrapper<StudentDo> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda();
        Page<StudentDo> page = new Page<>(1,5);
        IPage<StudentDo> iPage = myBatisPlusDemoService.page(page,queryWrapper);

        return BaseResponse.createResponse(iPage);
    }

    /**
     * 多表分页查询
     * @return
     */
    @GetMapping("/select/two")
    public BaseResponse mpTestSelectTwo(){
        return myBatisPlusDemoService.getTwoTableMsg();
    }


    /**
     * 单条数据插入
     */
    @PostMapping("/insert/oneMsg")
    public BaseResponse mpTestInsertOneMsg(@Valid  @RequestBody StudentDo studentDo){
        return myBatisPlusDemoService.saveOne(studentDo);
    }

    /**
     * 多条数据添加
     */
    @PostMapping("/insert/listMsg")
    public BaseResponse mpTestInsertListMsg(@Valid @RequestBody ValidList<StudentDo> list){
        return myBatisPlusDemoService.saveList(list);
    }


    /**
     * 数据修改
     */
    @PostMapping("/update/oneMsg")
    public BaseResponse mpTestUpdateListMsg(@Valid @RequestBody StudentDo studentDo){
        QueryWrapper<StudentDo> queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq(StudentDo::getStudentName,studentDo.getStudentName());
        boolean isSuccess = myBatisPlusDemoService.update(studentDo,queryWrapper);
        if (isSuccess){
            return BaseResponse.createBasicOK();
        }else {
            return BaseResponse.createError(ErrorMsg.INNER);
        }
    }
}
