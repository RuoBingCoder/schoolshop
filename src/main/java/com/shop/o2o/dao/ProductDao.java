package com.shop.o2o.dao;

import com.shop.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/19
 */
public interface ProductDao {
    /**
     * 插入商品
     *
     * @return
     */
    int insertProduct(Product product);

    /**
     * 更新商品
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 查询商品信息
     *
     * @param productId
     * @return
     */
    Product queryProductById(long productId);

    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id,商品类别
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(
            @Param("productCondition") Product productCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 商品计数
     *
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 删除商品类别之前，将商品类别ID置为空
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(@Param("ProductCategoryId") long productCategoryId);
}
