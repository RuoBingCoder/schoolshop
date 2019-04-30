package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/7
 * 店铺
 */
@Setter
@Getter
public class Shop {
    private Long shopId;
    private String shopName;
    private String shopDesc;
    private String shopAddr;
    private String phone;
    private String shopImg;
    /**
     * -1 不可用 0（审核） 1可用
     */
    private Integer enableStatus;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;
    /**
     * 超级管理员给店家提醒
     */
    private String advice;
    private Area area;
    private PersonInfo owner;
    private ShopCategory shopCategory;
}
