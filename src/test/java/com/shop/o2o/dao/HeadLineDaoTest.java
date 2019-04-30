package com.shop.o2o.dao;

import com.shop.o2o.BaseTest;
import com.shop.o2o.entity.HeadLine;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class HeadLineDaoTest extends BaseTest {

    @Autowired
    private HeadLineDao headLineDao;

    @Test
    public void queryHeadLineTest() {
        List<HeadLine> headLineList = headLineDao.queryHeadLine(new HeadLine());
        Assert.assertEquals(2, headLineList.size());
    }

}