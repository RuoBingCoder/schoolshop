package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/6
 * 区域
 */
@Setter
@Getter
public class Area {
    private Integer areaId;
    private String areaName;
    /**
     * 权重
     */
    private Integer priority;

    private Date createTime;

    private Date lastEditTime;
}
