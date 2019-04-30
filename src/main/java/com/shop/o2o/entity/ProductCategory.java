package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/7
 * 商品类别
 */
@Setter
@Getter
public class ProductCategory {
    private Long productCategoryId;
    private Long shopId;
    private String productCategoryName;
    private Integer priority;
    private Date createTime;
}
