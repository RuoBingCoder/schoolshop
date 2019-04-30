package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/7
 * 店铺类别
 */
@Getter
@Setter
public class ShopCategory {

    private Long shopCategoryId;
    private String shopCategoryName;
    /**描述*/
    private String shopCategoryDesc;
    private String shopCategoryImg;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    /**上级id*/
    private ShopCategory parent;
}
