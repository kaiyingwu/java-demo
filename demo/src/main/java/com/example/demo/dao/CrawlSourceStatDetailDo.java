package com.example.demo.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by watson on 2020/7/24
 */
@Setter
@Getter
@Accessors(chain = true)
public class CrawlSourceStatDetailDo {

    // 日期
    private String statDay;

    // 抓取量
    private long crawlCount;

    // 建模机审审核通过量
    private long autoVerifyCount;

    // 建模机审通过率
    private BigDecimal autoVerifyRate = BigDecimal.ZERO;;

    // 人审通过量
    private long manualVerifyCount;

    // 人审通过率
    private BigDecimal manualVerifyRate = BigDecimal.ZERO;;

    // 总入库量
    private long contTotalCount;

    // 总入库率
    private BigDecimal contTotalRate = BigDecimal.ZERO;;
}
