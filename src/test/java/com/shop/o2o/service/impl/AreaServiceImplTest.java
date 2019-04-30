package com.shop.o2o.service.impl;

import com.shop.o2o.BaseTest;
import com.shop.o2o.entity.Area;
import com.shop.o2o.service.AreaService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class AreaServiceImplTest extends BaseTest {
    @Autowired
    AreaService areaService;

    @Test
    public void getAreaList() {
        List<Area> areaList = areaService.getAreaList();
        Assert.assertEquals("东华", areaList.get(0).getAreaName());
        for (Area area : areaList) {
            System.out.println(area.getAreaName());

        }
    }
}