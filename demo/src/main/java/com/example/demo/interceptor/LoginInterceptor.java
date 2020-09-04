package com.example.demo.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import com.example.demo.bean.BaseResponse;
import com.example.demo.dao.StudentDto;
import com.example.demo.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCommands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    private String tokenKey = "TOKEN_SESSION";

    @Autowired
    private JedisCommands jedisCommands;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
        //HttpSession session = request.getSession();
        String token = request.getHeader(tokenKey);
        StudentDto studentDto = (StudentDto) request.getSession().getAttribute(token);
        // 根据sessionId检查是否登录
        if (StringUtils.isBlank(token)){
            response.getWriter().write(JsonUtil.toJsonString(BaseResponse.createError("没有token",10004)));
            return false;
        }

        if (studentDto ==null){
            if (jedisCommands.get(token) == null){
                response.getWriter().write(JsonUtil.toJsonString(BaseResponse.createError("未登录，请登录",10005)));
                return false;
            }
        }

        return true;
    }

    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) throws Exception {

    }
}
