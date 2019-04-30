package com.shop.o2o.service.impl;

import com.shop.o2o.dao.ShopCategoryDao;
import com.shop.o2o.entity.ShopCategory;
import com.shop.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/11
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    public List<ShopCategory> shopCategoryList(ShopCategory shopCategory) {

        return shopCategoryDao.shopCategoryList(shopCategory);
    }
}
