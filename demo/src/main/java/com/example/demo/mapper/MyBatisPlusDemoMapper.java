package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.StudentAndTeacherDo;
import com.example.demo.dao.StudentDo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Create by wky on 2020/9/21
 **/
@Mapper
@Component
public interface MyBatisPlusDemoMapper extends BaseMapper<StudentDo> {

    /**
     * 多表分页查询
     */
    @Results(value = {@Result(property = "studentName" ,column = "student_name"),
            @Result(property = "teacherName" ,column = "teacher_name") ,
            @Result(property = "studentId" ,column = "student_id"),
            @Result(property = "state",column = "state")})
    @Select("select s.id as student_id ,s.student_name,t.teacher_name,s.state from student s left join teacher t on s.id = t.id ${ew.customSqlSegment}")
    IPage<StudentAndTeacherDo> selectStudentAndTeacherPage(Page<StudentAndTeacherDo> page,@Param(Constants.WRAPPER) Wrapper<StudentAndTeacherDo> wrapper);


}
