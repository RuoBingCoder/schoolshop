package com.shop.o2o.enums;

import lombok.Getter;

/**
 * @author : 石建雷
 * @date :2019/4/19
 */
@Getter
public enum ProductStateEnum {
    /* 商品添加枚举*/
    SUCCESS(0, "添加成功！"),
    EMPTY(-1, "商品为空！"),

    ;

    private int code;
    private String msg;

    ProductStateEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ProductStateEnum stateOf(int value) {
        for (ProductStateEnum stateEnum :
                values()) {

            if (stateEnum.getCode() == value) {
                return stateEnum;
            }

        }
        return null;
    }
}
