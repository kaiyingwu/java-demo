package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MySqlServiceTest {

    @Autowired
    MySqlService mySqlService;

    @Test
    void insertStudent() {

        mySqlService.insertStudent("wukaiying","960514");

    }
}