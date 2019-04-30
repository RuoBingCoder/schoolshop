package com.shop.o2o.service;

import com.shop.o2o.entity.HeadLine;

import java.io.IOException;
import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/22
 */
public interface HeadLineService {
    /**
     * 根据传入的条件返回头条列表
     *
     * @param headLineCondition
     * @return
     */
    List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException;
}
