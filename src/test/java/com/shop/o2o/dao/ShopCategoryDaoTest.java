package com.shop.o2o.dao;

import com.shop.o2o.BaseTest;
import com.shop.o2o.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void shopCategoryListTest() {
        List<ShopCategory> categoryList = shopCategoryDao.shopCategoryList(new ShopCategory());
        for (ShopCategory shopCategory : categoryList) {
            System.out.println(shopCategory.getShopCategoryName());
        }


    }


}