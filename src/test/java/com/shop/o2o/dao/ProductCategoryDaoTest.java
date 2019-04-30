package com.shop.o2o.dao;

import com.shop.o2o.BaseTest;
import com.shop.o2o.entity.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductCategoryDaoTest extends BaseTest {
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void productShopCategory() {
        long shopId = 3L;
        List<ProductCategory> categoryList = productCategoryDao.queryCategoryList(shopId);

        System.out.println(categoryList.size());
    }

    @Test
    public void productCategoryAddInBatchesTest() {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setPriority(12);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(3L);
        productCategory1.setProductCategoryName("海底捞");
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setPriority(10);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(3L);
        productCategory2.setProductCategoryName("麻辣烫");

        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory1);
        productCategoryList.add(productCategory2);
        int num = productCategoryDao.productCategoryAddInBatches(productCategoryList);
        Assert.assertEquals(2, num);
    }

    @Test
    public void deleteProductCategoryTest() {
        int effectNum = productCategoryDao.deleteProductCategory(6L, 3L);
        System.out.println(effectNum);
        Assert.assertEquals(1, effectNum);
    }
}