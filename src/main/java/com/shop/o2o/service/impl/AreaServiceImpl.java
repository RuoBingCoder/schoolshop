package com.shop.o2o.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.o2o.cache.JedisUtil;
import com.shop.o2o.dao.AreaDao;
import com.shop.o2o.entity.Area;
import com.shop.o2o.exection.AreaOperationException;
import com.shop.o2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/8
 */
@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    AreaDao areaDao;
    @Autowired
    private JedisUtil.Keys jedisKeys;
    @Autowired
    private JedisUtil.Strings jedisStrings;
    public static String AREALISTKEY = "areaList";
    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Override
    public List<Area> getAreaList() {
        String key = AREALISTKEY;
        List<Area> areaList = null;
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)) {
            areaList = areaDao.query();
            try {
                String jsonStr = mapper.writeValueAsString(areaList);
                jedisStrings.set(key, jsonStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());

            }
        } else {
            String jsonStr = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
            try {
                areaList = mapper.readValue(jsonStr, javaType);
            } catch (IOException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new AreaOperationException(e.getMessage());
            }

        }
        return areaList;


    }
}
