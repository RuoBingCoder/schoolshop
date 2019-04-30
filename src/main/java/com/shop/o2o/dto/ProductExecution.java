package com.shop.o2o.dto;

import com.shop.o2o.entity.Product;
import com.shop.o2o.enums.ProductCategoryStateEnum;
import com.shop.o2o.enums.ProductStateEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/19
 */
@Setter
@Getter
public class ProductExecution {

    private int status;
    private String msg;
    private int count;
    /**
     * 查询单个商品
     */
    private Product product;
    /**
     * 查询商品列表
     */
    private List<Product> productList;

    public ProductExecution() {
    }

    public ProductExecution(ProductStateEnum stateEnum) {
        this.status = stateEnum.getCode();
        this.msg = stateEnum.getMsg();
    }


    /**
     * 操作成功返回的构造器
     */
    public ProductExecution(ProductStateEnum stateEnum, Product product) {
        this.product = product;
        this.status = stateEnum.getCode();
        this.msg = stateEnum.getMsg();

    }

    /**
     * 操作成功返回的构造器
     *
     * @param stateEnum
     * @param productList
     */
    public ProductExecution(ProductStateEnum stateEnum, List<Product>  productList) {
        this.productList = productList;
        this.status = stateEnum.getCode();
        this.msg = stateEnum.getMsg();

    }


}
