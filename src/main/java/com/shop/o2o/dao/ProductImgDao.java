package com.shop.o2o.dao;

import com.shop.o2o.entity.ProductImg;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/19
 */

public interface ProductImgDao {
    /**
     * 批量插入图片
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 删除指定商品下的所有截图
     *
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(long productId);

    List<ProductImg> queryProductImgList(long productId);
}
