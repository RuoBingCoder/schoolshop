package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/7
 * 商品
 */
@Setter
@Getter
public class Product {

    private Long productId;
    private String productName;
    private String productDesc;
    //简略图
    private String imgAddr;
    private String normalPrice;
    private String promotionPrice;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    //-1.不可用，0.下架1.在前端展示系统展示
    private Integer enableStatus;
    private List<ProductImg> productImgList;
    private ProductCategory productCategory;
    private Shop shop;
}
