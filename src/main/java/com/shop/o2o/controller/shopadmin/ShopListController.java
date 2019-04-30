package com.shop.o2o.controller.shopadmin;

import com.shop.o2o.dto.ShopExecution;
import com.shop.o2o.entity.Area;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.entity.ShopCategory;
import com.shop.o2o.service.AreaService;
import com.shop.o2o.service.ShopCategoryService;
import com.shop.o2o.service.ShopService;
import com.shop.o2o.util.HttpRequestServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 石建雷
 * @date :2019/4/24
 */
@RestController
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopService shopService;

    /**
     * 返回商品列表shopCategory列表（一级或二级）以及区域信息列表
     *
     * @param request
     * @return
     */
    @GetMapping("/listshopspageinfo")
    public Map<String, Object> listShopsPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();

        long parentId = HttpRequestServletUtil.getLong(request, "parentId");
        List<ShopCategory> shopCategoryList = null;

        if (parentId != -1L) {
            //如果parentId存在，则取出该一级ShopCategory下的二级ShopCategory列表
            try {
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parent = new ShopCategory();
                parent.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parent);
                shopCategoryList = shopCategoryService.shopCategoryList(shopCategoryCondition);

            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
        } else {
            //如果parentId不存在，则取出所有一级ShopCategory（用户在首页选择的是全部商店列表）
            try {
                shopCategoryList = shopCategoryService.shopCategoryList(null);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }


        }
        modelMap.put("shopCategoryList", shopCategoryList);
        List<Area> areaList;
        try {
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);

        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;

    }

    /**
     * 获取店铺列表和店铺总数
     *
     * @param request
     * @return
     */
    @GetMapping("/listshops")
    public Map<String, Object> listShops(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpRequestServletUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestServletUtil.getInt(request, "pageSize");
        if ((pageIndex > -1) && (pageSize > -1)) {
            /*获取一级类别id*/
            long parentId = HttpRequestServletUtil.getLong(request, "parentId");
            /*获取二级类别id*/
            long shopCategoryId = HttpRequestServletUtil.getLong(request, "shopCategoryId");
            /*获取区域id*/
            long areaId = HttpRequestServletUtil.getLong(request, "areaId");
            /*获取模糊查询的名字*/
            String shopName = HttpRequestServletUtil.getString(request, "shopName");
            /*获取组合后查询的条件*/
            Shop shopCondition = compactShopConditionSearch(parentId, shopCategoryId, areaId, shopName);
            /*根据查询条件返回分页信息获取店铺列表，并返回总数*/
            ShopExecution shopExecution = shopService.queryShopList(shopCondition, pageIndex, pageSize);
            modelMap.put("shopList", shopExecution.getShopList());
            modelMap.put("count", shopExecution.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageIndex or pageSize");
        }

        return modelMap;

    }

    /**
     * 组合查询条件，将条件封装到compactShopConditionSearch中
     *
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compactShopConditionSearch(long parentId, long shopCategoryId, long areaId, String shopName) {
        Shop shopCondition = new Shop();
        if (parentId != -1L) {
            /*查询某个一级店铺下的二级店铺的所有列表*/
            ShopCategory shopCategory = new ShopCategory();
            ShopCategory parent = new ShopCategory();
            parent.setShopCategoryId(parentId);
            shopCategory.setParent(parent);
            shopCondition.setShopCategory(shopCategory);
        }
        if (shopCategoryId != -1L) {
            /*查询某个二级店铺的所有列表*/
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);

        }
        if (areaId != -1L) {
            /*查询某个区域Id下的店铺列表*/
            Area area = new Area();
            shopCondition.setArea(area);

        }
        if (shopName != null) {
            /*查询名字里包含ShopName的店铺列表*/
            shopCondition.setShopName(shopName);

        }
        /*前端展示的都是审核同过的店铺*/
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
