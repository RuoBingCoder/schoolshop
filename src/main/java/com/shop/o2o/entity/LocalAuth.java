package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/7
 * 本地账号
 */
@Setter
@Getter
public class LocalAuth {
    private Long localAuthId;
    private String userName;
    private String password;
    private Date createTime;
    private Date lastEditTime;
    private PersonInfo personInfo;


}
