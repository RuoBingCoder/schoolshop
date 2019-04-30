package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/7
 * 商品图片
 */
@Setter
@Getter
public class ProductImg {
    private Long productImgId;
    private String imgAddr;
//    图片说明
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Long productId;


}
