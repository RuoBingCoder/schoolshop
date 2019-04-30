package com.shop.o2o.dto;

import com.shop.o2o.entity.Shop;
import com.shop.o2o.enums.ShopStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/9
 */
@Setter
@Getter
public class ShopExecution {
    private Integer state;
    /**
     * 状态标识
     */
    private String stateInfo;
    /**
     * 店铺数量
     */
    private Integer count;
    private Shop shop;

    private List<Shop> shopList;

    public ShopExecution() {
    }

    /**
     * @param shopStateEnum 店铺操作失败使用的构造器
     */

    public ShopExecution(ShopStateEnum shopStateEnum) {
        this.state = shopStateEnum.getCode();
        this.stateInfo = shopStateEnum.getMessage();

    }

    /**
     * @param shopStateEnum
     * @param shop          店铺操作成功使用的构造器
     */

    public ShopExecution(ShopStateEnum shopStateEnum, Shop shop) {
        this.shop = shop;
        this.state = shopStateEnum.getCode();
        this.stateInfo = shopStateEnum.getMessage();

    }

    /**
     * @param shopStateEnum
     * @param shopList      店铺操作成功使用的构造器
     */

    public ShopExecution(ShopStateEnum shopStateEnum, List<Shop> shopList) {
        this.state = shopStateEnum.getCode();
        this.stateInfo = shopStateEnum.getMessage();
        this.shopList = shopList;

    }
}
