package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/7
 * 微信
 */
@Getter
@Setter
public class WeChatAuth {
    private Long weChatAuthId;
    private Long openId;
    private Date createTime;
    private PersonInfo personInfo;

}
