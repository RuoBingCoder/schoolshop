package com.shop.o2o.service.impl;

import com.shop.o2o.BaseTest;
import com.shop.o2o.dto.ImgHolder;
import com.shop.o2o.dto.ProductExecution;
import com.shop.o2o.entity.Product;
import com.shop.o2o.entity.ProductCategory;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.enums.ProductStateEnum;
import com.shop.o2o.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceImplTest extends BaseTest {
    @Autowired
    private ProductService productService;


    @Test
    public void addProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(3L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setProductName("测试产品2");
        product.setProductDesc("测试产品2");
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setPriority(20);
        product.setEnableStatus(ProductStateEnum.SUCCESS.getCode());
        product.setProductCategory(productCategory);
        product.setShop(shop);
//        创建文件缩略图
        File file = new File("H:/1、图、影、音/图片/壁纸/Mac原生超高清壁纸/Abstract.jpg");
        InputStream inputStream = new FileInputStream(file);
        ImgHolder imgHolder = new ImgHolder(file.getName(), inputStream);
        File file1 = new File("H:/1、图、影、音/图片/壁纸/Mac原生超高清壁纸/AbstractShapes.jpg");
        InputStream inputStream1 = new FileInputStream(file1);
        File file2 = new File("H:/1、图、影、音/图片/壁纸/Mac原生超高清壁纸/Lake.jpg");
        InputStream inputStream2 = new FileInputStream(file2);
        List<ImgHolder> imgHolderList = new ArrayList<ImgHolder>();
        imgHolderList.add(new ImgHolder(file1.getName(), inputStream1));
        imgHolderList.add(new ImgHolder(file2.getName(), inputStream2));

        ProductExecution productException = productService.addProduct(product, imgHolder, imgHolderList);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getCode(), productException.getStatus());
    }

    @Test
    public void modifyProductTest() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(3L);
        product.setProductId(10L);
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1L);
        product.setProductName("正式产品2");
        product.setProductDesc("正式产品2");
        product.setCreateTime(new Date());
        product.setLastEditTime(new Date());
        product.setPriority(20);
        product.setEnableStatus(ProductStateEnum.SUCCESS.getCode());
        product.setProductCategory(productCategory);
        product.setShop(shop);
//        创建文件缩略图
        File file = new File("H:/1、图、影、音/图片/壁纸/Mac原生超高清壁纸/Mountain_Range.jpg");
        InputStream inputStream = new FileInputStream(file);
        ImgHolder imgHolder = new ImgHolder(file.getName(), inputStream);
        File file1 = new File("H:/1、图、影、音/图片/壁纸/Mac原生超高清壁纸/Wave.jpg");
        InputStream inputStream1 = new FileInputStream(file1);
        File file2 = new File("H:/1、图、影、音/图片/壁纸/Mac原生超高清壁纸/Snow.jpg");
        InputStream inputStream2 = new FileInputStream(file2);
        List<ImgHolder> imgHolderList = new ArrayList<ImgHolder>();
        imgHolderList.add(new ImgHolder(file1.getName(), inputStream1));
        imgHolderList.add(new ImgHolder(file2.getName(), inputStream2));

        ProductExecution productException = productService.modifyProduct(product, imgHolder, imgHolderList);
        Assert.assertEquals(ProductStateEnum.SUCCESS.getCode(), productException.getStatus());
    }


}