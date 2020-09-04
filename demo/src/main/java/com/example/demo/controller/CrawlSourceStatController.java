package com.example.demo.controller;


import com.example.demo.bean.BaseResponse;
import com.example.demo.service.CrawlSourceStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;

@RestController
@RequestMapping("/crawl")
public class CrawlSourceStatController {

    @Autowired
    private CrawlSourceStatService crawlSourceStatService;

    /**
     * 整体数据 / 源链接查看详情
     * @param id 源ID
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param field 排序规则(0:日期; 1:抓取量; 2:建模机审通过量; 3:建模机审通过率; 4:人审通过量; 5:人审通过率; 6:总入库量; 7:总入库率)
     * @param sort 0:升序; 1:降序
     * @param pageNo 页码
     * @param pageSize 页数
     */
    @GetMapping("/stat")
    public BaseResponse getCrawlSourceStatDetail(@RequestParam(required = false) Integer id,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate,
                                                 @RequestParam(required = false, defaultValue = "0") Integer field,
                                                 @RequestParam(required = false, defaultValue = "1") Integer sort,
                                                 @Min(value = 1, message = "分页页号不合法，必须从1开始")
                                                 @RequestParam(value = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                                 @Min(value = 1, message = "每页个数不合法，必须从1开始")
                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {

        return BaseResponse.createResponse(crawlSourceStatService.selectCrawSourceStat(id, field, sort, startDate, endDate, pageNo, pageSize));
    }

}
