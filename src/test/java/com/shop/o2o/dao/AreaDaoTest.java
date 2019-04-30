package com.shop.o2o.dao;

import com.shop.o2o.BaseTest;
import com.shop.o2o.entity.Area;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Slf4j
public class AreaDaoTest extends BaseTest {

    @Autowired
    private AreaDao areaDao;

    @Test
    public void areaList() {

        List<Area> areaList = areaDao.query();
        log.info("========【信息】========areaList={}",areaList.toString());


        Assert.assertEquals(2, areaList.size());
    }

}