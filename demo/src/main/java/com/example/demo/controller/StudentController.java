package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.bean.BaseResponse;
import com.example.demo.dao.StudentAndTeacherDo;
import com.example.demo.dao.StudentDo;
import com.example.demo.dao.TeacherDo;
import com.example.demo.impl.MySqlServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private MySqlServiceImpl mySqlServiceImpl;

    /**
     * 添加一个学生
     * @param name
     * @param password
     * @return
     */
    @GetMapping("/add")
    public int addNewStudent(@RequestParam(value = "name", required = true) String name,
                             @RequestParam(value = "password" , required = true) String password){
        return mySqlServiceImpl.insertStudent(name,password);
    }


    /**
     * 添加一个学生并返回自增主键
     * @param name
     * @param password
     * @return
     */
    @GetMapping("/addId")
    public int addNewStudentGetId(String name,String password){
        return mySqlServiceImpl.insertStudentGetId(name,password);
    }


    /**
     * 添加一个学生并返回非自增主键
     * @param name
     * @param password
     * @return
     */
    @GetMapping("/addIdNoUp")
    public int addNewStudentGetNoUpId(String name,String password){
        return mySqlServiceImpl.insertStudentGetIdNoUp(name,password);
    }


    /**
     * 删除一个学生的数据
     */
    @GetMapping("/delStudent")
    public int delStudent(String name){
        return mySqlServiceImpl.delStudent(name);
    }

    /**
     * 修改一个学生的数据
     */
    @GetMapping("/updateStudent")
    public int update(int state,String name){
        return mySqlServiceImpl.updateStudent(state,name);
    }


    /**
     * 获取所有学生的数量
     */
    @GetMapping("/getAllStudentNum")
    public int selectStudentNum(){
        List<StudentDo> list = mySqlServiceImpl.selectStudentAll();
        return list.size();
    }

    /**
     * 获取所有学生的数据
     */
    @GetMapping("/getAllStudent")
    public List<StudentDo> selectStudent(){
        return mySqlServiceImpl.selectStudentAll();
    }

    /**
     * 根据条件获取学生数据
     */
    @GetMapping("/getStudentById")
    public List<StudentDo> selectStudentById(@RequestParam(value = "studentId") int studentId){
        return mySqlServiceImpl.selectStudentById(studentId);
    }

    /**
     * 连表查询学生和老师的数据表
     */
    @GetMapping("/select")
    public BaseResponse selectStudentAndTeacher(){
        try {
            return BaseResponse.createResponse(mySqlServiceImpl.selectStudentAndTeacher());
        }catch (Exception e) {
            return BaseResponse.createError("错误",10001);
        }
    }


    /**
     * 连表查询学生和老师的数据，带分页
     */
    @GetMapping("/selectPage")
    public BaseResponse selectStudentAndTeacherPage(int page , int size){
        try {
            return BaseResponse.createResponse(mySqlServiceImpl.selectStudentAndTeacherPage(page,size));
        } catch (Exception e) {
            return BaseResponse.createError("错误",10001);
        }
    }


    /**
     * post 方式测试全局异常处理是否生效
     */
    @PostMapping("/selectTeacher")
    public BaseResponse selectTeacher(@Validated @RequestBody TeacherDo teacherDo){
        try {
            return BaseResponse.createResponse(mySqlServiceImpl.selectTeacher(teacherDo.getId()));
            //return BaseResponse.createResponse(teacherDo.toString());
        }catch (Exception e){
            return BaseResponse.createError("错误",10001);
        }
    }
}
