package com.shop.o2o.dao;

import com.shop.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/8
 */

public interface ShopDao {
    /**
     * 查询店铺
     *
     * @param id
     * @return
     */

    Shop queryByShopId(long id);

    /**
     * 添加店铺信息
     *
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺信息
     *
     * @param shop
     * @return
     */

    int updateShop(Shop shop);

    /**
     * 分页查询店铺，可输入查询店铺的名字（模糊），店铺状态，店铺类别，店铺区域
     *
     * @param shopCondition 店铺条件参数
     * @param rowIndex      从第几行开始查询
     * @param pageSize      数据条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList 总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
