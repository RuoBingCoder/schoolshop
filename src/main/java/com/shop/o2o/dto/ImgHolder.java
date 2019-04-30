package com.shop.o2o.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.InputStream;

/**
 * @author : 石建雷
 * @date :2019/4/19
 */
@Getter
@Setter
public class ImgHolder {

    private InputStream inputStream;
    private String imageName;

    public ImgHolder(String imageName, InputStream inputStream) {
        this.imageName = imageName;
        this.inputStream = inputStream;
    }

    public ImgHolder() {
    }
}
