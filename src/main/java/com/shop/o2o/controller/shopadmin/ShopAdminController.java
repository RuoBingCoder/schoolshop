package com.shop.o2o.controller.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : 石建雷
 * @date :2019/4/10
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopAdminController {

    @GetMapping("/shoppage")
    public String shopPage() {
        return "shop/shopoperator";
    }

    @GetMapping("/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }


    @GetMapping("/productshopcategorylist")
    public String productShopCategoryList() {
        return "shop/productcategorymanagement";
    }

    @GetMapping("/shoplist")
    public String shopList() {
        return "shop/shopList";
    }

    @GetMapping("/productoperation")
    public String productOperation() {
        return "/shop/productoperation";
    }

    @GetMapping("/productlist")
    public String productList() {
        return "/shop/productlist";
    }




}
