package com.shop.o2o.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author : 石建雷
 * @date :2019/4/7
 * 头条
 */
@Setter
@Getter
public class HeadLine {
    private Long lineId;
    private String lineName;
    private String lineLink;
    private String lineImg;
    private Integer priority;
    /**0 不可用 1课用*/
    private Integer enableStatus;
    private Date createTime;

    private Date lastEditTime;
}
