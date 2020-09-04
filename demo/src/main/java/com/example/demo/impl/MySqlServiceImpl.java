package com.example.demo.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.StudentAndTeacherDo;
import com.example.demo.dao.StudentDo;
import com.example.demo.dao.StudentDto;
import com.example.demo.mapper.MySqlMapper;
import com.example.demo.service.MySqlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCommands;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.UUID;

@Slf4j
@Service
public class MySqlServiceImpl implements MySqlService {

    @Autowired
    private MySqlMapper mySql;

    @Autowired
    private JedisCommands jedisCommands;

    private static final String USER_TICKET_PRE = "user_ticket_pre";
    private static final int TIME = 12 * 60 * 60;
    //private static final int TIME = 30;


    @Override
    public Integer insertStudent(String name, String password) {
        return mySql.integer(name,password);
    }

    @Override
    public Integer insertStudentGetId(String name, String pwd) {
        StudentDo studentDo = new StudentDo();
        studentDo.setStudentName(name);
        studentDo.setStudentPassword(pwd);
        mySql.addStudentId(studentDo);
        return studentDo.getId();
    }


    @Override
    public Integer insertStudentGetIdNoUp(String name, String pwd) {
        StudentDo studentDo = new StudentDo();
        studentDo.setStudentName(name);
        studentDo.setStudentPassword(pwd);
        mySql.addStudentIdNoUp(studentDo);
        return studentDo.getId();
    }

    @Override
    public Integer delStudent(String name) {
       return mySql.delStudent(name);
    }

    @Override
    public Integer updateStudent(int state, String name) {
        return mySql.updateStudent(state,name);
    }

    @Override
    public List<StudentDo> selectStudentAll() {
        return mySql.selectStudent();
    }

    @Override
    public List<StudentDo> selectStudentById(int studentId) {
        return mySql.selectStudentById(studentId);
    }

    @Override
    public StudentDto loginStudent(String name, String password, HttpServletRequest request) throws Exception{
        //数据库获取学生数据，并获得studentDo，不包含token
       StudentDo studentDo = mySql.studentLogin(name,password);
       if (studentDo == null){
           return null;
       }

       //将StudentDo数据复制给StudentDto
       StudentDto studentDto = studentDo.clone(StudentDto.class);

       //查找缓存内是否存在ticket
       String ticket = jedisCommands.get(USER_TICKET_PRE+name);

       //如果ticker为空，即缓存内不存在或者缓存已经过期
        if (StringUtils.isBlank(ticket)) {
            // 生成ticket，并将用户信息存储在redis中
            ticket = UUID.randomUUID().toString().replace("-", "");
        }
        studentDto.setToken(ticket);

        //用户对应ticket
        jedisCommands.set(USER_TICKET_PRE+name,ticket);
        jedisCommands.expire(USER_TICKET_PRE+name,TIME);

        //ticket对应用户数据
        jedisCommands.set(ticket, JSON.toJSONString(studentDto));
        jedisCommands.expire(ticket, TIME);

        // 用户信息存入session
        request.getSession().setAttribute(studentDto.getToken(), studentDto);
        //设置Session数据保留的最长时间
        //request.getSession().setMaxInactiveInterval(TIME);
        return studentDto;
    }

    /**
     * HttpServletRequest 中的token 没有时间线存在
     * jedisCommands 中的token 根据时间设定 来进行清除
     * @param token
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public StudentDto loginStudentByToken(String token, HttpServletRequest request) throws Exception {
        StudentDto studentDto = (StudentDto) request.getSession().getAttribute(token);
        if (studentDto!=null){
            return studentDto;
        }
        String studentStr = jedisCommands.get(token);
        if (StringUtils.isNotBlank(studentStr)){
            studentDto = JSON.parseObject(studentStr,StudentDto.class);
            if (studentDto!=null){
                return studentDto;
            }
        }
        return null;
    }


    /**
     *
     * 用户退出
     */
    @Override
    public void studentLogout(String name, HttpServletRequest request) throws Exception {
        String ticket = jedisCommands.get(USER_TICKET_PRE + name);
        if (StringUtils.isNotBlank(ticket)){
            jedisCommands.del(ticket);
            jedisCommands.del(USER_TICKET_PRE+name);
            request.getSession().removeAttribute(ticket);
        }
    }

    /**
     * 多表查询
     * @return
     * @throws Exception
     */
    @Override
    public List<StudentAndTeacherDo> selectStudentAndTeacher() throws Exception {

        QueryWrapper<StudentAndTeacherDo> queryWrapper = Wrappers.<StudentAndTeacherDo>query();
        //queryWrapper.eq("s.student_name","wukaiying");

        List<StudentAndTeacherDo> list = mySql.selectStudentAndTeacher(queryWrapper);
        return list;
    }

    @Override
    public IPage<StudentAndTeacherDo> selectStudentAndTeacherPage(int page,int size) throws Exception {
        Page<StudentAndTeacherDo> page1 = new Page<>(page,size);
        log.info(String.valueOf(page));
        log.info(String.valueOf(size));
        QueryWrapper<StudentAndTeacherDo> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("s.student_name","wukaiying");
        queryWrapper.orderByDesc("student_id");

        IPage<StudentAndTeacherDo> iPage = mySql.selectStudentAndTeacherPage(page1,queryWrapper);

//        Page<StudentAndTeacherDo> resultPage = new Page<>();
//        BeanUtils.copyProperties(iPage, resultPage);
//        List<StudentAndTeacherDo> list = new ArrayList<>();
//        iPage.getRecords().forEach(item -> {
//            StudentAndTeacherDo vo = item;
//            list.add(vo);
//        });
//        resultPage.setRecords(list);


        return iPage;
    }

    @Override
    public String selectTeacher(int id) {
        return mySql.selectTeacher(id);
    }
}
