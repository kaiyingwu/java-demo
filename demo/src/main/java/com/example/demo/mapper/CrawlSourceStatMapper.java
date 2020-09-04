package com.example.demo.mapper;

import com.example.demo.dao.CrawlSourceStatDetailDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface CrawlSourceStatMapper {
    /**
     * queryToday 1: 只查询今天; 2:查询范围包含今天; 3: 查询范围不包含今天
     * 查询今天使用 crawl_source_stat_detail 详情表查询
     * 历史数据使用 crawl_source_stat 统计之后的数据查询
     * crawl_source_stat 由 crawl_source_stat_detail 统计归类之后的数据
     */
    @Select("<script>" +
            "<if test = 'queryToday != 1'>" +
            "select stat_day as statDay, sum(state_count) as crawlCount, sum(auto_verify) as autoVerifyCount, round(sum(auto_verify) / sum(state_count), 4) as autoVerifyRate, " +
            "sum(manual_verify) as manualVerifyCount, round(sum(manual_verify) / sum(state_count), 4) as manualVerifyRate, " +
            "sum(auto_verify + manual_verify) as contTotalCount, round(sum(auto_verify + manual_verify) / sum(state_count), 4) as contTotalRate " +
            "from crawl_source_stat " +
            "<where>" +
            "<if test = 'id != null and id != \"\"'>" +
            "and crawl_source_id = #{id} " +
            "</if>" +
            "<if test = 'startDate != null and startDate != \"\"'>" +
            "and stat_day &gt;= #{startDate} " +
            "</if>" +
            "<if test = 'endDate != null and endDate != \"\"'>" +
            "and stat_day &lt;= #{endDate} " +
            "</if>" +
            "</where>" +
            "group by stat_day " +
            "</if>" +
            "<if test = 'queryToday == 2'>" +
            " UNION ALL " +
            "</if>" +
            "<if test = 'queryToday != 3'>" +
            "select stat_day as statDay, count(state) as crawlCount, sum(auto_verify) as autoVerifyCount, round(sum(auto_verify) / count(state), 4) as autoVerifyRate, " +
            "sum(manual_verify) as manualVerifyCount, round(sum(manual_verify) / count(state), 4) as manualVerifyRate, " +
            "sum(auto_verify + manual_verify) as contTotalCount, round(sum(auto_verify + manual_verify) / count(state), 4) as contTotalRate " +
            "from crawl_source_stat_detail where stat_day = #{endDate} " +
            "<if test = 'id != null and id != \"\"'>" +
            "and crawl_source_id = #{id} " +
            "</if>" +
            "group by stat_day " +
            "</if>" +
            "<choose>" +
            "<when test = 'field == 0'>" +
            "order by statDay " +
            "</when>" +
            "<when test = 'field == 1'>" +
            "order by crawlCount " +
            "</when>" +
            "<when test = 'field == 2'>" +
            "order by autoVerifyCount " +
            "</when>" +
            "<when test = 'field == 3'>" +
            "order by autoVerifyRate " +
            "</when>" +
            "<when test = 'field == 4'>" +
            "order by manualVerifyCount " +
            "</when>" +
            "<when test = 'field == 5'>" +
            "order by manualVerifyRate " +
            "</when>" +
            "<when test = 'field == 6'>" +
            "order by contTotalCount " +
            "</when>" +
            "<when test = 'field == 7'>" +
            "order by contTotalRate " +
            "</when>" +
            "</choose>" +
            "<choose>" +
            "<when test = 'sort == 0'>" +
            "asc " +
            "</when>" +
            "<otherwise>" +
            "desc " +
            "</otherwise>" +
            "</choose>" +
            "</script>")
    List<CrawlSourceStatDetailDo> getCrawlSourceStatDetail(
            @Param("id") Integer id,
            @Param("field") Integer field,
            @Param("sort") Integer sort,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("queryToday") int queryToday);



    /**
     * queryToday 1: 只查询今天; 2:查询范围包含今天; 3: 查询范围不包含今天
     * 查询今天使用 crawl_source_stat_detail 详情表查询
     * 历史数据使用 crawl_source_stat 统计之后的数据查询
     * crawl_source_stat 由 crawl_source_stat_detail 统计归类之后的数据
     */
    @Select("<script>"+
            "select stat_day as statDay , " +
            "sum(state_count) as crawlCount," +
            "sum(auto_verify) as autoVerifyCount," +
            "round(sum(auto_verify) / sum(state_count), 4) as autoVerifyRate," +
            "sum(manual_verify) as manualVerifyCount," +
            "round(sum(manual_verify) / sum(state_count), 4) as manualVerifyRate," +
            "sum(auto_verify + manual_verify) as contTotalCount," +
            "round(sum(auto_verify + manual_verify) / sum(state_count), 4) as contTotalRate " +
            "from crawl_source_stat"+
            "<where>"+
            "<if test='id !=null and id != \"\"'>" +
            "and crawl_source_id = #{id}"+
            "</if>"+
            "<if test='startDate != null and startDate != \"\"'>"+
            "and stat_day &gt;= #{startDate}"+
            "</if>"+
            "<if test='endDate != null and endDate != \"\"'>" +
            "and stat_day &lt;= #{endDate}"+
            "</if>"+
            "</where>"+
            "group by stat_day"+
            "<choose>" +
            "<when test = 'field == 0'>" +
            "order by statDay " +
            "</when>" +
            "<when test = 'field == 1'>" +
            "order by crawlCount " +
            "</when>" +
            "<when test = 'field == 2'>" +
            "order by autoVerifyCount " +
            "</when>" +
            "<when test = 'field == 3'>" +
            "order by autoVerifyRate " +
            "</when>" +
            "<when test = 'field == 4'>" +
            "order by manualVerifyCount " +
            "</when>" +
            "<when test = 'field == 5'>" +
            "order by manualVerifyRate " +
            "</when>" +
            "<when test = 'field == 6'>" +
            "order by contTotalCount " +
            "</when>" +
            "<when test = 'field == 7'>" +
            "order by contTotalRate " +
            "</when>" +
            "</choose>" +
            "<choose>" +
            "<when test = 'sort == 0'>" +
            "asc " +
            "</when>" +
            "<otherwise>" +
            "desc " +
            "</otherwise>" +
            "</choose>" +
            "</script>")
    List<CrawlSourceStatDetailDo> getCrawlSourceStatDetailNew(
            @Param("id") Integer id,
            @Param("field") Integer field,
            @Param("sort") Integer sort,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);
}
