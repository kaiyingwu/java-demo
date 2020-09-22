package com.example.demo.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.bean.BaseResponse;
import com.example.demo.dao.StudentAndTeacherDo;
import com.example.demo.dao.StudentDo;
import com.example.demo.enums.ErrorMsg;
import com.example.demo.mapper.MyBatisPlusDemoMapper;
import com.example.demo.service.MyBatisPlusDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by wky on 2020/9/21
 **/

@Service
public class MyBatisPlusDemoImpl extends ServiceImpl<MyBatisPlusDemoMapper,StudentDo> implements MyBatisPlusDemoService {

    @Autowired
    MyBatisPlusDemoMapper myBatisPlusDemoMapper;

    @Override
    public BaseResponse getTwoTableMsg() {
        Page<StudentAndTeacherDo> page = new Page<>(1,5);
        QueryWrapper<StudentAndTeacherDo> queryWrapper = new QueryWrapper();
        //若需要特殊条件可以这样添加,连表需要表名那张表的字段，如果没有转义的话，一切已sql中的定义为准
        queryWrapper.eq("s.state",1);
        queryWrapper.orderByDesc("student_id");
        IPage<StudentAndTeacherDo> iPage = myBatisPlusDemoMapper.selectStudentAndTeacherPage(page,queryWrapper);
        //如果需要连表查询后数据的数量即统计：iPage.getTotal();
        return BaseResponse.createResponse(iPage);
    }

    @Override
    public BaseResponse saveOne(StudentDo studentDo) {
        try {
            //在这之前需要添加一个查重操作
            QueryWrapper<StudentDo> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(StudentDo::getStudentName,studentDo.getStudentName());
            int count = this.count(queryWrapper);
            if (count>0){
                return BaseResponse.createError(ErrorMsg.STUDENT_NAME_HAVE);
            }else {
                this.save(studentDo);
            }
        }catch (Exception e){
            return BaseResponse.createError(ErrorMsg.INNER);
        }
        return BaseResponse.createBasicOK();
    }

    @Override
    public BaseResponse saveList(List<StudentDo> studentDos) {
        try {
            studentDos.removeIf(StudentDo->{
                QueryWrapper<StudentDo> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda()
                        .eq(com.example.demo.dao.StudentDo::getStudentName,StudentDo.getStudentName());
                int count = this.count(queryWrapper);
                return count>0;
            });
            this.saveBatch(studentDos);
        }catch (Exception e){
            return BaseResponse.createError(ErrorMsg.INNER);
        }
        return BaseResponse.createBasicOK();
    }


}
