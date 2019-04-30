package com.shop.o2o.exection;

import com.shop.o2o.entity.Shop;

/**
 * @author : 石建雷
 * @date :2019/4/9
 */

public class ShopOperationException extends RuntimeException {

    public ShopOperationException(String msg) {
        super(msg);
    }
}
