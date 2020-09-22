package com.example.demo.impl.bookkeeping;

import com.example.demo.bean.BKBaseResponse;
import com.example.demo.dao.bookkeeping.UserDao;
import com.example.demo.mapper.bookkeeping.UserMapper;
import com.example.demo.service.bookkeeping.UserSerVice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class UserServiceImpl implements UserSerVice {

    @Autowired
    private UserMapper userMapper;

    @Override
    public BKBaseResponse getUser(String account, String password) {

        boolean isHave = userMapper.getUserById(account)!=0;
        if (!isHave){
           return BKBaseResponse.createError("用户不存在",10003);
        }

        UserDao userDao = userMapper.getUser(account,password);
        if (userDao == null){
            return BKBaseResponse.createError("密码错误",10002);
        }
        String ticket = UUID.randomUUID().toString().replace("-", "");
        userDao.setToken(ticket);

        return BKBaseResponse.createResponseAndMsg(userDao,"登录成功");
    }

}
