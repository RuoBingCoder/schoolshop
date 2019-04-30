package com.shop.o2o.dao;

import com.shop.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/10
 * 商品类目
 */

public interface ShopCategoryDao {
    /**
     * 商品类目列表
     *
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> shopCategoryList(@Param("shopCategoryCondition") ShopCategory shopCategoryCondition);


}
