package com.shop.o2o.dao;

import com.shop.o2o.entity.HeadLine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/22
 */

public interface HeadLineDao {
    /**
     * 根据传入的查询的条件（头条名查询头条）
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> queryHeadLine(@Param("headLineCondition") HeadLine headLineCondition);
}
