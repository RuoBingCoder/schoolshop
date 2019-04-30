package com.shop.o2o.enums;

import lombok.Getter;

/**
 * @author : 石建雷
 * @date :2019/4/17
 */
@Getter
public enum ProductCategoryStateEnum {
    /**
     * 商品类目
     */
    INNER_ERROR(-1001, "内部信息错误！"),
    INSERT_SUCCESS(1000, "添加成功！"),
    EMPTY_LIST(-1002, "列表为空！"),
    DELETE_SUCCESS(1, "删除成功！"),
    ;

    private int code;
    private String msg;

    ProductCategoryStateEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public static ProductCategoryStateEnum statusOf(int index) {
        for (ProductCategoryStateEnum stateEnum : values()) {
            if (stateEnum.getCode() == index) {
                return stateEnum;
            }

        }
        return null;
    }
}
