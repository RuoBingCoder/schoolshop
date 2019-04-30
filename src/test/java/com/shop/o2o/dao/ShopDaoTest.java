package com.shop.o2o.dao;

import com.shop.o2o.BaseTest;
import com.shop.o2o.entity.Area;
import com.shop.o2o.entity.PersonInfo;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.entity.ShopCategory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ShopDaoTest extends BaseTest {
    @Autowired
    private ShopDao shopDao;

    @Test
    public void shopAdd() {
        Shop shop = new Shop();
        PersonInfo personInfo = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();
        personInfo.setUserId(1L);
        area.setAreaId(1);
        shopCategory.setShopCategoryId(1L);

        shop.setArea(area);
        shop.setOwner(personInfo);
        shop.setShopCategory(shopCategory);
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setLastEditTime(new Date());
        shop.setCreateTime(new Date());
        shop.setPhone("123");
        shop.setShopAddr("大同大学");
        shop.setShopImg("test");
        shop.setShopName("喜得龙");
        shop.setShopDesc("好东西");
        shop.setPriority(1);

        int effectNum = shopDao.insertShop(shop);
        Assert.assertEquals(1, effectNum);
    }

    @Test
    public void updateShop() {
        Shop shop = new Shop();
        shop.setShopId(1L);
        shop.setEnableStatus(1);
        shop.setAdvice("审核中");
        shop.setLastEditTime(new Date());

        shop.setPhone("18235271111");
        shop.setShopAddr("山西大同市");
        shop.setShopImg("test");
        shop.setShopName("喜得龙");
        shop.setShopDesc("产品测试");
        shop.setPriority(1);

        int updateNum = shopDao.updateShop(shop);
        Assert.assertEquals(1, updateNum);
    }

    @Test
    public void queryByShop() {
        long id = 1L;
        Shop shop = shopDao.queryByShopId(id);
        System.out.println("AreaId:" + shop.getArea().getAreaId());
        System.out.println("AreaName:" + shop.getArea().getAreaName());

    }

    @Test
    public void queryShopListTest() {
        Shop shopCondition = new Shop();
       /* PersonInfo owner = new PersonInfo();
        owner.setUserId(1L);
        shopCondition.setOwner(owner);*/
        ShopCategory shopCategory = new ShopCategory();
        ShopCategory parent = new ShopCategory();
        parent.setShopCategoryId(5L);
        shopCategory.setParent(parent);
        shopCondition.setShopCategory(shopCategory);
        shopCondition.setShopName("f");
        List<Shop> shopList = shopDao.queryShopList(shopCondition, 0, 1);
        Assert.assertEquals(1, shopList.size());
        for (Shop shop1 : shopList) {
            System.out.println("========="+shop1.getShopName());

        }
        System.out.println("店铺的大小：" + shopList.size());
        int count = shopDao.queryShopCount(shopCondition);
        System.out.println("店铺的总数：" + count);

    }

    @Test
    public void shopListLike() {
        Shop shop = new Shop();
        shop.setShopName("麦");
        List<Shop> shopList = shopDao.queryShopList(shop, 0, 1);
        Assert.assertEquals(1, shopList.size());


    }
}