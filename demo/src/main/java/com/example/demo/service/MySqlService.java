package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dao.StudentAndTeacherDo;
import com.example.demo.dao.StudentDo;
import com.example.demo.dao.StudentDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MySqlService {

    Integer insertStudent(String name,String pwd);

    Integer insertStudentGetId(String name,String pwd);

    Integer insertStudentGetIdNoUp(String name,String pwd);

    Integer delStudent(String name);

    Integer updateStudent(int state,String name);

    List<StudentDo> selectStudentAll();

    List<StudentDo> selectStudentById(int studentId);

    StudentDto loginStudent(String name, String password, HttpServletRequest request) throws Exception;

    StudentDto loginStudentByToken(String token,HttpServletRequest request)throws Exception;

    void studentLogout(String name,HttpServletRequest request) throws Exception;

    List<StudentAndTeacherDo> selectStudentAndTeacher() throws Exception;

    IPage<StudentAndTeacherDo> selectStudentAndTeacherPage(int page,int size) throws Exception;

    String selectTeacher(int id) throws Exception;
}
