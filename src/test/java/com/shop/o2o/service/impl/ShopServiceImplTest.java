package com.shop.o2o.service.impl;

import com.shop.o2o.BaseTest;
import com.shop.o2o.dto.ImgHolder;
import com.shop.o2o.dto.ShopExecution;
import com.shop.o2o.entity.Area;
import com.shop.o2o.entity.PersonInfo;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.entity.ShopCategory;
import com.shop.o2o.enums.ShopStateEnum;
import com.shop.o2o.exection.ShopOperationException;
import com.shop.o2o.service.ShopService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Date;

public class ShopServiceImplTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void shopAdd() throws IOException {
        Shop shop = new Shop();
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        personInfo.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
//        shop.setShopId(19L);
        shop.setOwner(personInfo);
        shop.setShopCategory(shopCategory);
        shop.setArea(area);
        shop.setEnableStatus(ShopStateEnum.CHECK.getCode());
        shop.setAdvice("审核中");
        shop.setLastEditTime(new Date());

        shop.setPhone("1823526555");
        shop.setShopAddr("江苏南京");

        shop.setShopName("戴尔中国官方旗舰店");
        shop.setShopDesc("test");
        shop.setPriority(2);
        File file = new File("H:/1、图、影、音/图片/壁纸/Mac原生超高清壁纸/AbstractShapes.jpg");
        InputStream stream = new FileInputStream(file);
        ImgHolder imgHolder = new ImgHolder(file.getName(), stream);
        ShopExecution shopExection = shopService.shopAdd(shop, imgHolder);
        Assert.assertEquals(ShopStateEnum.CHECK.getCode(), shopExection.getState());

    }

    @Test
    public void shopModify() throws ShopOperationException, FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(17L);
        shop.setShopName("修改后的店铺4");
        File file = new File("H:/1、图、影、音/图片/壁纸/Mac原生超高清壁纸/AbstractShapes.jpg");
        InputStream inputStream = new FileInputStream(file);
        ImgHolder imgHolder = new ImgHolder("AbstractShapes.jpg", inputStream);
        ShopExecution shopExection = shopService.shopModify(shop, imgHolder);
        System.out.println("新的图片地址：" + shopExection.getShop().getShopImg());

    }

    @Test
    public void queryShopListTest() {
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(5L);
        shopCategory.setParent(parent);
        shop.setShopCategory(shopCategory);
        shop.setShopName("f");
        ShopExecution shopException = shopService.queryShopList(shop, 1, 1);
        System.out.println("店铺列表总数：" + shopException.getShopList().size());
        System.out.println("店铺总数：" + shopException.getCount());

    }
}