package com.shop.o2o.dao;

import com.shop.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/17
 */
public interface ProductCategoryDao {
    /**
     * 获取商品类别列表
     *
     * @param shopId
     * @return
     */
    List<ProductCategory> queryCategoryList(long shopId);

    /**
     * 批量增加商品类别
     *
     * @param productCategories
     * @return
     */
    int productCategoryAddInBatches(List<ProductCategory> productCategories);

    /**
     * 删除商品
     *
     * @param productCategoryId
     * @param shopId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
