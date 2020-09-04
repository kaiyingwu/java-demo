package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dao.CrawlSourceStatDetailDo;
import com.example.demo.dao.CrawlSourceStatDetailVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface CrawlSourceStatService {

    PageInfo<CrawlSourceStatDetailDo> selectCrawSourceStat(Integer id, Integer field, Integer sort, String startDate, String endDate, Integer pageNo, Integer pageSize);

}
