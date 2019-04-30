package com.shop.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : 石建雷
 * @date :2019/4/11
 * 验证码处理
 */

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
    //获取验证码值
        String verify = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
    //获取输入验证码
        String verifyCodeActual = HttpRequestServletUtil.getString(request, "verifyCodeActual");
        if (verifyCodeActual == null || !verifyCodeActual.equals(verify)) {
            return false;
        } else {

            return true;
        }

    }
}
