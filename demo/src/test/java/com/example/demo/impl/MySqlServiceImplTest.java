package com.example.demo.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MySqlServiceImplTest {

    @Autowired
    MySqlServiceImpl mySqlService;

    @Test
    void insertStudent() {

        mySqlService.insertStudent("wukaiying","960514");

    }
}