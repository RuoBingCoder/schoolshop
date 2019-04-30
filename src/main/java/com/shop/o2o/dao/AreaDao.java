package com.shop.o2o.dao;

import com.shop.o2o.entity.Area;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/7
 */

public interface AreaDao {


    List<Area> query();


}
