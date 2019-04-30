package com.shop.o2o.service;

import com.shop.o2o.dto.ImgHolder;
import com.shop.o2o.dto.ShopExecution;
import com.shop.o2o.entity.Shop;

/**
 * @author : 石建雷
 * @date :2019/4/9
 */

public interface ShopService {
    /**
     * @param shop
     * @param imgHolder
     *
     * @return 添加店铺信息
     */
    ShopExecution shopAdd(Shop shop, ImgHolder imgHolder);

    /**
     * @param shop
     * @param imgHolder
     *
     * @return 修改店铺信息
     */
    ShopExecution shopModify(Shop shop, ImgHolder imgHolder);

    /**
     * @param id
     * @return 查询店铺信息
     */
    Shop queryByShopId(long id);

    /**
     * 分页查询店铺，可输入查询店铺的名字（模糊），店铺状态，店铺类别，店铺区域
     *
     * @param shopCondition 店铺条件参数
     * @param rowIndex      从第几行开始查询,第几页
     * @param pageSize      数据条数
     * @return
     */
    ShopExecution queryShopList(Shop shopCondition, int pageIndex, int pageSize);


}
