package com.example.demo.util;

import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Component
public class MySqlUtil {

    @Autowired
    private  JdbcTemplate jdbcTemplate;
    /**
     * 判断用户名是否存在
     * @param name
     * @return
     */
    public  boolean isHasUser(int name){
        String sql = "select * from student where name = "+name;
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if (list.size()>0){
            return true;
        }else {
            return false;
        }
    }

}
