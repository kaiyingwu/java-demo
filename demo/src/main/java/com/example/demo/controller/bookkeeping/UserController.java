package com.example.demo.controller.bookkeeping;


import com.example.demo.bean.BKBaseResponse;
import com.example.demo.service.bookkeeping.UserSerVice;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/book/keeping")
public class UserController {

    @Autowired
    private UserSerVice userSerVice;

    @GetMapping(value = "/getUser")
    public BKBaseResponse getUser(@RequestParam(value = "account") String account, String password){

        if (StringUtils.isEmpty(account)&&StringUtils.isEmpty(password)){
            return BKBaseResponse.createError("账号密码不能为空",10001);
        }
        return userSerVice.getUser(account,password);

    }

}
