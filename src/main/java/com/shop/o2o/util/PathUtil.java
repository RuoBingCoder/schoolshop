package com.shop.o2o.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author : 石建雷
 * @date :2019/4/8
 */
@Slf4j
public class PathUtil {
    private static String separator = System.getProperty("file.separator");

    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";

        if (os.toLowerCase().startsWith("win")) {

            basePath = "H:/images/project";

        } else {
            basePath = "H:/JianLeiComputer/Pictures";
        }
        basePath = basePath.replace("/", separator);

        return basePath;
    }

    public static String getShopImgPath(long shopId) {
        StringBuilder shopImagePathBuilder = new StringBuilder();
        shopImagePathBuilder.append("/shop/image/");
        shopImagePathBuilder.append(shopId);
        shopImagePathBuilder.append("/");
        String shopImagePath = shopImagePathBuilder.toString().replace("/",
                separator);
        log.info("【shopPath】shopPath:{}", shopImagePath);
        return shopImagePath;


    }


}
