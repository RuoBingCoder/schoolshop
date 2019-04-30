package com.shop.o2o.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author : 石建雷
 * @date :2019/4/27
 * 数据库加密
 * DES对称加密算法
 */

public class DesUtil {
    private static Key key;
    /**
     * 设置密钥
     */
    private static String KEY_STR = "myKey";
    private static String CHARSETNAME = "UTF-8";
    private static String ALGOBRITHM = "DES";

    static {
        try {
            /*生成DES算法对象*/
            KeyGenerator generator = KeyGenerator.getInstance(ALGOBRITHM);
            /*运用SHA1算法安全策略*/
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            /*设置上密钥种子*/
            secureRandom.setSeed(KEY_STR.getBytes());
            /*初始化SHA1算法对象*/
            generator.init(secureRandom);
            /*生辰密钥对象*/
            key = generator.generateKey();
            generator = null;


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取加密后的信息
     *
     * @param str
     * @return
     */
    public static String getEncryptString(String str) {
        /*基于base64编码，接受byte[]转换String*/
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try {
            /*按utf8编码*/
            byte[] bytes = str.getBytes(CHARSETNAME);
            /*获取加密对象*/
            Cipher cipher = Cipher.getInstance(ALGOBRITHM);
            /*初始化加密信息*/
            cipher.init(Cipher.ENCRYPT_MODE, key);
            /*加密*/
            byte[] doFinal = cipher.doFinal(bytes);

            return base64Encoder.encode(doFinal);

        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    /**
     * 获取解密后的信息
     */
    public static String getDecryptString(String str) {

        /*基于base64编码，接受byte[]转换String*/
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            /*将字符串str解密为byte[]*/
            byte[] bytes = base64Decoder.decodeBuffer(str);
            /*获取解密对象*/
            Cipher cipher = Cipher.getInstance(ALGOBRITHM);
            /*初始化加密信息*/
            cipher.init(Cipher.DECRYPT_MODE, key);
            /*解密*/
            byte[] doFinal = cipher.doFinal(bytes);

            return new String(doFinal, CHARSETNAME);
        } catch (Exception e) {
            throw new RuntimeException();

        }

    }

    public static void main(String[] args) {
        System.out.println(getEncryptString("root"));
        System.out.println(getEncryptString("123456"));

    }
}