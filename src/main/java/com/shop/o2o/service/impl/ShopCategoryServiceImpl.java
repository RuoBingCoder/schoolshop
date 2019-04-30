package com.shop.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.o2o.cache.JedisUtil;
import com.shop.o2o.dao.ShopCategoryDao;
import com.shop.o2o.entity.HeadLine;
import com.shop.o2o.entity.ShopCategory;
import com.shop.o2o.exection.HeadlineOperationException;
import com.shop.o2o.service.ShopCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/11
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    public static String SHOPCATEGORYLIST = "shopcategorylist";
    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);


    @Override
    public List<ShopCategory> shopCategoryList(ShopCategory shopCategoryCondition) {
        List<ShopCategory> shopCategoryList = null;
        String key = SHOPCATEGORYLIST;
        ObjectMapper mapper = new ObjectMapper();

        if (shopCategoryCondition == null) {
            /*若查询条件为空列出所有首页大类，即parentid为空的店铺类别*/
            key = key + "_allfirstlevel";
        } else if (shopCategoryCondition != null && shopCategoryCondition.getParent() != null
                && shopCategoryCondition.getParent().getShopCategoryId() != null) {
            /*若parentid非空，则列出parentid下所有的子类别*/
            key = key + "_parent" + shopCategoryCondition.getParent().getShopCategoryId();

        } else if (shopCategoryCondition != null) {
            /*列出所有子类别不管属于哪个类，都列出来*/
            key = key + "_allsecondlevel";
        }

        if (!jedisKeys.exists(key)) {
            shopCategoryList = shopCategoryDao.shopCategoryList(shopCategoryCondition);
            String jsonStr = null;
            try {
                jsonStr = mapper.writeValueAsString(shopCategoryList);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedisStrings.set(key, jsonStr);
        } else {
            String jsonStr = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                shopCategoryList = mapper.readValue(jsonStr, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadlineOperationException(e.getMessage());
            }
        }

        return shopCategoryList;
    }
}
