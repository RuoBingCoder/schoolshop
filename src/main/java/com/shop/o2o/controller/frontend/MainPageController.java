package com.shop.o2o.controller.frontend;

import com.shop.o2o.entity.HeadLine;
import com.shop.o2o.entity.ShopCategory;
import com.shop.o2o.service.HeadLineService;
import com.shop.o2o.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 石建雷
 * @date :2019/4/22
 */
@RequestMapping("/frontend")
@RestController
public class MainPageController {

    @Autowired
    private HeadLineService headLineService;
    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 初始化给前端页面展示主页信息，获取一级店铺列表以及头条列表
     *
     * @return
     */
    @GetMapping("/listmainpageinfo")
    public Map<String, Object> listMainPageInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList;
        try {
            /*获取一级店铺类别列表（即parentId为null的shopCategory）*/
            shopCategoryList = shopCategoryService.shopCategoryList(null);
            modelMap.put("shopCategoryList", shopCategoryList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        List<HeadLine> headLineList;
        try {
            /*获取状态为可用（1）*/
            HeadLine headLine = new HeadLine();
            headLine.setEnableStatus(1);
            headLineList = headLineService.getHeadLineList(headLine);
            modelMap.put("headLineList", headLineList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }

        modelMap.put("success", true);
        return modelMap;

    }
}
