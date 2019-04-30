package com.shop.o2o.dao;

import com.shop.o2o.BaseTest;
import com.shop.o2o.entity.ProductImg;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductImgDaoTest extends BaseTest {
    @Autowired
    private ProductImgDao productImgDao;


    @Test
    public void batchInsertProductImg() {
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("1");
        productImg1.setImgDesc("图片1");
        productImg1.setCreateTime(new Date());
        productImg1.setPriority(1);
        productImg1.setProductId(1L);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("2");
        productImg2.setImgDesc("图片2");
        productImg2.setCreateTime(new Date());
        productImg2.setPriority(1);
        productImg2.setProductId(1L);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectNum = productImgDao.batchInsertProductImg(productImgList);
        Assert.assertEquals(2, effectNum);

    }


    @Test
    public void deleteProductImgByProductIdTest() {
        long productId = 1L;
        int effectNum = productImgDao.deleteProductImgByProductId(productId);
        Assert.assertEquals(2, effectNum);

    }

}