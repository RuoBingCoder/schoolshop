package com.shop.o2o.util;

/**
 * @author : 石建雷
 * @date :2019/4/16
 * 分页转换工具类
 */

public class PageCalculator {
    /**
     * @param pageIndex 页码
     * @param pageSize  页显示多少条数据
     * @return
     */
    public static int calculatorRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;

    }


}
