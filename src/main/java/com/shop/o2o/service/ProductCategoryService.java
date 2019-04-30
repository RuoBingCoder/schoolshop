package com.shop.o2o.service;

import com.shop.o2o.dto.ProductCategoryExecution;
import com.shop.o2o.entity.ProductCategory;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/17
 * 商品类目
 */

public interface ProductCategoryService {

    List<ProductCategory> getProductCategoryList(long shopId);


    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList);

    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId);
}
