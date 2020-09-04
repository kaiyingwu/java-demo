package com.example.demo.controller;

import com.example.demo.bean.BaseResponse;
import com.example.demo.dao.StudentDto;
import com.example.demo.service.MySqlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/login")
public class LoginTestController {

    @Autowired
    private MySqlService mySqlService;

    @RequestMapping("/byName")
    public Object studentLogin(@RequestParam(value = "name") String name, @RequestParam(value = "password") String password, HttpServletRequest request){
        try {
            if (!validUsernameAndPassword(name,password)){
                return BaseResponse.createError("用户名密码为空",10001);
            }
            StudentDto studentDto = mySqlService.loginStudent(name,password,request);

            if (studentDto==null){
                return BaseResponse.createError("登录失败请检查用户名和密码是否正确",10002);
            }
            return BaseResponse.createResponse(studentDto);
        }catch (Exception e){
            log.error("user login error. username:{}", name, e);
            return BaseResponse.createError("inner Error", 10003);
        }
    }

    @RequestMapping("/byToken")
    public Object studentLoginByToken(String token,HttpServletRequest request){
        try {
            StudentDto studentDto = mySqlService.loginStudentByToken(token,request);
            if (studentDto == null){
                return BaseResponse.createError("用户未登录",10004);
            }
            return BaseResponse.createResponse(studentDto);
        } catch (Exception e) {
            log.error("user login error. token:{}", token, e);
            return BaseResponse.createError("inner Error", 10003);
        }
    }

    @RequestMapping("/logout")
    public Object studentLogout(String name,HttpServletRequest request){
        try {
            mySqlService.studentLogout(name,request);
            return BaseResponse.createBasicOK();
        } catch (Exception e) {
            log.error("user logout error. name:{}", name, e);
            return BaseResponse.createError("inner Error", 10003);
        }
    }

    private boolean validUsernameAndPassword(String username, String password) {
        return StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password);
    }
}
