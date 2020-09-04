package com.example.demo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.CrawlSourceStatDetailDo;
import com.example.demo.dao.CrawlSourceStatDetailVo;
import com.example.demo.mapper.CrawlSourceStatMapper;
import com.example.demo.service.CrawlSourceStatService;
import com.example.demo.util.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class CrawlSourceStatImpl implements CrawlSourceStatService {


    @Autowired
    private CrawlSourceStatMapper crawlSourceStatMapper;

    @Override
    public PageInfo<CrawlSourceStatDetailDo> selectCrawSourceStat(Integer id, Integer field, Integer sort, String startDate, String endDate, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        int queryToday = queryToday(startDate, endDate);
        System.out.println(queryToday+"");
        List<CrawlSourceStatDetailDo> iPage = crawlSourceStatMapper.getCrawlSourceStatDetailNew(id, field, sort, startDate, endDate);
        PageInfo page = new PageInfo(iPage);
        //page.setList(iPage);
        return page;
    }


    /**
     * 1: 只查询今天; 2:查询范围包含今天; 3: 查询范围不包含今天
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     */
    private int queryToday(String startDate, String endDate) {

        String today = DateUtil.DateToString(new Date(), DateUtil.NO_TIME_DATE_FORMAT);
        if (StringUtils.equals(startDate, endDate)) {
            return StringUtils.equals(endDate, today) ? 1 : 3;
        } else {
            return StringUtils.equals(endDate, today) ? 2 : 3;
        }
    }


}
