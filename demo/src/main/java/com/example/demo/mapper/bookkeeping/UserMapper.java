package com.example.demo.mapper.bookkeeping;


import com.example.demo.dao.bookkeeping.UserDao;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    @Select("select id,user_name,nick_name from book_keeping_user where account = #{account} and password = #{password}")
    @Results(value = {@Result(column = "account",property = "account"),
                      @Result(column = "password",property = "password")})
    UserDao getUser(@Param("account") String account, @Param("password") String password);

    @Select("select count(*) from book_keeping_user where account = #{account}")
    Integer getUserById(String account);

}
