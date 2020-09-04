package com.example.demo.controller;


import com.example.demo.bean.BaseResponse;
import com.example.demo.dao.InfoSummary;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/getSummary")
    public BaseResponse getSummary(String infoId){
        InfoSummary infoSummary = testService.getSummary(infoId);
       return BaseResponse.createResponse(infoSummary);
    }
}
