package com.shop.o2o.util;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * @author : 石建雷
 * @date :2019/4/27
 */

public class EncryptPropertyPlaceholderConfiger extends PropertyPlaceholderConfigurer {

    /**
     * 需要加密的的字符数组
     */
    private String[] encryptProNames = {"jdbc.username", "jdbc.password"};

    /**
     * 对关键的属性进行转换
     */
    @Override
    protected String convertProperty(String propertyName, String propertyValue) {
        if (isEncryptPro(propertyName)) {
            /*对已加密字段进行解密*/
            String decryptValue = DesUtil.getDecryptString(propertyValue);
            return decryptValue;
        } else {
            return propertyValue;
        }
    }

    /**
     * 该属性是否加密
     *
     * @param propertyName
     * @return
     */
    private boolean isEncryptPro(String propertyName) {
        for (String encryptPropertyName : encryptProNames) {
            if (encryptPropertyName.equals(propertyName)) {
                return true;
            }
        }
        return false;
    }
}
