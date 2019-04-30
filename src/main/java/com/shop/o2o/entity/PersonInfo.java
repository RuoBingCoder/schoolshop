package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/6
 */
@Setter
@Getter
public class PersonInfo {

    private Long userId;
    private String name;
    private String profileImg;
    private String email;
    private String gender;

    private Integer enableStatus;
    /**
     * 顾客 店家 超级管理员
     */
    private Integer userType;
    private Date createTime;
    private Date lastEditTime;


}
