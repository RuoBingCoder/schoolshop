package com.shop.o2o.enums;

import lombok.Getter;

/**
 * @author : 石建雷
 * @date :2019/4/9
 */
@Getter
public enum ShopStateEnum {
    /**
     * 枚举
     */
    CHECK(0, "审核中"),
    OFFLINE(-1, "非法店铺"),
    SUCCESS(1, "操作成功"),
    PASS(2, "认证通过"),
    INNER_ERROR(-1001, "内部系统错误"),
    NULL_SHOP_ID(-1002, "shopId为空"),
    NULL_SHOP(-1003, "shop为空"),
    ;
    private Integer code;
    private String message;

    ShopStateEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取value值
     */
    public static ShopStateEnum stateOf(int value) {
        for (ShopStateEnum stateEnum :
                values()) {

            if (stateEnum.getCode() == value) {
                return stateEnum;
            }

        }
        return null;
    }

}
