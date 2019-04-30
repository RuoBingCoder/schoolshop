package com.shop.o2o.dto;

import com.shop.o2o.entity.ProductCategory;
import com.shop.o2o.enums.ProductCategoryStateEnum;
import com.shop.o2o.util.PageCalculator;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/18
 */
@Setter
@Getter
public class ProductCategoryExecution {
    private int code;
    private String msg;
    private List<ProductCategory> productCategoryList;

    public ProductCategoryExecution() {
    }

    //操作失败返回的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum) {
        this.code = stateEnum.getCode();
        this.msg = stateEnum.getMsg();
    }

    //操作成功返回的构造器
    public ProductCategoryExecution(ProductCategoryStateEnum stateEnum, List<ProductCategory> productCategoryList) {
        this.code = stateEnum.getCode();
        this.msg = stateEnum.getMsg();
        this.productCategoryList = productCategoryList;
    }
}
