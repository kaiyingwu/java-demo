package com.example.demo.mapper;

import com.example.demo.dao.InfoSummary;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface TestMapper {

    /**
     * 文章类获取摘要
     */
    @Results(value = {@Result(column = "info_id",property = "infoId"),
            @Result(column = "before_summary",property = "beforeSummary"),
            @Result(column = "after_summary",property = "afterSummary")})
    @Select("select info_id,before_summary,after_summary from info_summary where info_id = #{infoId}")
    InfoSummary selectByInfoSummaryId(@Param("infoId")String infoId);


}
