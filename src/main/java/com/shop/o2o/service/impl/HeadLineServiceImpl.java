package com.shop.o2o.service.impl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.o2o.cache.JedisUtil;
import com.shop.o2o.dao.HeadLineDao;
import com.shop.o2o.entity.HeadLine;
import com.shop.o2o.exection.HeadlineOperationException;
import com.shop.o2o.service.HeadLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/22
 */
@Service
public class HeadLineServiceImpl implements HeadLineService {
    @Autowired
    private HeadLineDao headLineDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    public static String HEADLINELIST = "headList";
    private static Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);


    @Override
    public List<HeadLine> getHeadLineList(HeadLine headLineCondition) throws IOException {
        List<HeadLine> headLineList = null;
        String key = HEADLINELIST;
        ObjectMapper mapper = new ObjectMapper();
        /*拼接出redis的key*/
        if (headLineCondition != null && headLineCondition.getEnableStatus() != null) {
            key = key + "_" + headLineCondition.getEnableStatus();
        }
        if (!jedisKeys.exists(key)) {
            headLineList = headLineDao.queryHeadLine(headLineCondition);
            String jsonStr = mapper.writeValueAsString(headLineList);
            jedisStrings.set(key, jsonStr);
        } else {
            String jsonStr = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
            try {
                headLineList = mapper.readValue(jsonStr, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new HeadlineOperationException(e.getMessage());
            }
        }
        return headLineList;
    }
}
