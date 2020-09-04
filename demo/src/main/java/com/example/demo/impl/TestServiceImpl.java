package com.example.demo.impl;

import com.example.demo.dao.InfoSummary;
import com.example.demo.mapper.TestMapper;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    TestMapper testMapper;

    @Override
    public InfoSummary getSummary(String infoId) {
        return testMapper.selectByInfoSummaryId(infoId);
    }

}
