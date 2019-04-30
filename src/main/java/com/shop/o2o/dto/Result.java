package com.shop.o2o.dto;

import com.shop.o2o.enums.ProductCategoryStateEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : 石建雷
 * @date :2019/4/17
 * 状态工具类
 */
@Setter
@Getter
public class Result<T> {
    private boolean success;
    private T data;
    private int errCode;
    private String errMsg;

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, T data, int errCode, String errMsg) {
        this.success = success;
        this.data = data;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public Result(boolean success, int errCode, ProductCategoryStateEnum stateEnum) {
    }

    public Result(boolean success, int errCode, String errMsg) {
        this.success = success;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                ", errCode=" + errCode +
                ", errMsg='" + errMsg + '\'' +
                '}';
    }
}
