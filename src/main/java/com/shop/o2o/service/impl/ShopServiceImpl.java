package com.shop.o2o.service.impl;

import com.shop.o2o.dao.ShopDao;
import com.shop.o2o.dto.ImgHolder;
import com.shop.o2o.dto.ShopExecution;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.enums.ShopStateEnum;
import com.shop.o2o.exection.ShopOperationException;
import com.shop.o2o.service.ShopService;
import com.shop.o2o.util.ImgUtil;
import com.shop.o2o.util.PageCalculator;
import com.shop.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/9
 * 店铺
 */
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopDao shopDao;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopExecution shopAdd(Shop shop, ImgHolder imgHolder) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);

        }
        //给店铺信息赋予初始值
        shop.setPriority(0);
        shop.setCreateTime(new Date());
        shop.setLastEditTime(new Date());
        //添加店铺信息
        int effectNum = shopDao.insertShop(shop);
        if (effectNum < 0) {
            throw new ShopOperationException("店铺创建失败");
        } else {
            if (imgHolder.getInputStream() != null) {
                // 存储图片
                try {
                    addShopImg(shop, imgHolder);
                } catch (Exception e) {
                    throw new ShopOperationException("shopImgException: error:{}" + e.getMessage());
                }
                // 更新图片
                effectNum = shopDao.updateShop(shop);
                if (effectNum < 0) {
                    throw new ShopOperationException("更新图片失败");
                }
            }

        }

        return new ShopExecution(ShopStateEnum.CHECK, shop);
    }


    @Override
    public ShopExecution shopModify(Shop shop, ImgHolder imgHolder) throws ShopOperationException {
        if (shop == null && shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
//            判断是否要处理照片
                if (imgHolder.getInputStream() != null && imgHolder.getImageName() != null && !"".equals(imgHolder.getImageName())) {
                    Shop shopTemp = shopDao.queryByShopId(shop.getShopId());
                    if (shopTemp.getShopImg() != null) {
                        ImgUtil.deleteImgPath(shopTemp.getShopImg());

                    }
                    addShopImg(shop, imgHolder);
                }
//            更新店铺信息
                shop.setLastEditTime(new Date());
                int effectNum = shopDao.updateShop(shop);
                if (effectNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("shoperror" + e.getMessage());
            }
        }


    }


    @Override
    public Shop queryByShopId(long id) {
        return shopDao.queryByShopId(id);
    }

    @Override
    public ShopExecution queryShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution shopException = new ShopExecution();
        if (shopList != null) {

            shopException.setShopList(shopList);
            shopException.setCount(count);
        } else {
            shopException.setState(ShopStateEnum.INNER_ERROR.getCode());
        }

        return shopException;
    }


    @Transactional(rollbackFor = Exception.class)
    public void addShopImg(Shop shop, ImgHolder imgHolder) {
//        获取图片的相对路径
        String dest = PathUtil.getShopImgPath(shop.getShopId());
        String shopImgAddr = ImgUtil.generateThumbnail(imgHolder, dest);
        shop.setShopImg(shopImgAddr);
    }
}
