package com.shop.o2o.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : 石建雷
 * @date :2019/4/25
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {
    /**
     * 首页路由
     *
     * @return
     */
    @GetMapping("/frontendindex")
    public String frontendIndex() {
        return "/frontend/index";
    }

    /**
     * 店铺详情路由
     *
     * @return
     */
    @GetMapping("/shoplist")
    public String shopListTwo() {
        return "/frontend/shoplist";

    }

    /**
     * 商品详情路由
     *
     * @return
     */
    @GetMapping("/productdetail")
    public String productDetail() {
        return "/frontend/shopdetail";

    }
}
