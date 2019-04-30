package com.shop.o2o.dao;

import com.shop.o2o.BaseTest;
import com.shop.o2o.entity.Product;
import com.shop.o2o.entity.ProductCategory;
import com.shop.o2o.entity.ProductImg;
import com.shop.o2o.entity.Shop;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductDaoTest extends BaseTest {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void insertProduct() {
        Shop shop = new Shop();
        shop.setShopId(3L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        Product product1 = new Product();
        product1.setProductId(1L);
        product1.setProductName("测试1");
        product1.setProductDesc("测试1");
        product1.setImgAddr("1");
        product1.setPriority(2);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setEnableStatus(1);
        product1.setShop(shop);
        product1.setProductCategory(productCategory);

        Product product2 = new Product();
        product2.setProductId(1L);
        product2.setProductName("测试2");
        product2.setProductDesc("测试2");
        product2.setImgAddr("1");
        product2.setPriority(2);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setEnableStatus(1);
        product2.setShop(shop);
        product1.setProductCategory(productCategory);

        Product product3 = new Product();
        product3.setProductId(1L);
        product3.setProductName("测试3");
        product3.setProductDesc("测试3");
        product3.setImgAddr("1");
        product3.setPriority(2);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setEnableStatus(1);
        product3.setShop(shop);
        product1.setProductCategory(productCategory);

        int effectNum1 = productDao.insertProduct(product1);
        Assert.assertEquals(1, effectNum1);
        int effectNum2 = productDao.insertProduct(product2);
        Assert.assertEquals(1, effectNum2);
        int effectNum3 = productDao.insertProduct(product3);
        Assert.assertEquals(1, effectNum3);
    }

    @Test
    public void queryProductByIdTest() {
        Long productId = 1L;
        ProductImg productImg1 = new ProductImg();
        productImg1.setProductId(productId);
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setCreateTime(new Date());
        productImg1.setPriority(1);
        ProductImg productImg2 = new ProductImg();
        productImg2.setProductId(productId);
        productImg2.setImgAddr("图片2");
        productImg2.setImgDesc("测试图片2");
        productImg2.setCreateTime(new Date());
        productImg2.setPriority(1);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);

        int effectNum = productImgDao.batchInsertProductImg(productImgList);

        Assert.assertEquals(2, effectNum);
        Product product = productDao.queryProductById(productId);
        Assert.assertEquals(2, product.getProductImgList().size());

        int effectNum1 = productImgDao.deleteProductImgByProductId(productId);
        Assert.assertEquals(2, effectNum1);

    }

    @Test
    public void queryProductListTest() {
        Product productCondition = new Product();
       /* Assert.assertEquals(2, productList.size());
        int count = productDao.queryProductCount(productCondition);
        Assert.assertEquals(10, count);*/

//        使用商品名称进行模糊查询
        productCondition.setProductName("粥");
        List<Product> productList = productDao.queryProductList(productCondition, 0, 2);
        Assert.assertEquals(2, productList.size());
        int count = productDao.queryProductCount(productCondition);
        System.out.println("count:======" + count);
        Assert.assertEquals(2, count);


    }

    @Test
    public void updateProduct() {
        Product product = new Product();
        ProductCategory productCategory = new ProductCategory();
        Shop shop = new Shop();
        shop.setShopId(3L);
        productCategory.setProductCategoryId(1L);
        product.setShop(shop);
        product.setProductCategory(productCategory);
        product.setProductName("第二个产品");
        product.setProductId(4L);
        int effectNum = productDao.updateProduct(product);
        Assert.assertEquals(1, effectNum);
    }

}