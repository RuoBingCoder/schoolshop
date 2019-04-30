package com.shop.o2o.controller.frontend;

import com.shop.o2o.dto.ProductExecution;
import com.shop.o2o.entity.Product;
import com.shop.o2o.entity.ProductCategory;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.service.AreaService;
import com.shop.o2o.service.ProductCategoryService;
import com.shop.o2o.service.ProductService;
import com.shop.o2o.service.ShopService;
import com.shop.o2o.util.HttpRequestServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 石建雷
 * @date :2019/4/26
 */
@RestController
@RequestMapping("/frontend")
public class ShopDetailController {
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ShopService shopService;

    /**
     * 获取商品类别列表和店铺信息
     *
     * @param request
     * @return
     */
    @GetMapping("/listshopdetailpageInfo")
    public Map<String, Object> listShopDetailPageInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpRequestServletUtil.getLong(request, "shopId");
        Shop shop = null;
        List<ProductCategory> productCategoryList;
        if (shopId != -1) {
            /*获取店铺Id为shopId的店铺信息*/
            shop = shopService.queryByShopId(shopId);
            /*获取店铺下的商品列表*/
            productCategoryList = productCategoryService.getProductCategoryList(shopId);
            modelMap.put("shop", shop);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("erMsg", "empty shopId");
        }
        return modelMap;
    }

    /**
     * 依据条件查询分页列出店铺下的所有商品列表
     *
     * @param request
     * @return
     */
    @GetMapping("/listproductsbypage")
    public Map<String, Object> listProductsByPage(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        int pageIndex = HttpRequestServletUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestServletUtil.getInt(request, "pageSize");
        /*获取店铺Id*/
        long shopId = HttpRequestServletUtil.getLong(request, "shopId");
        boolean temp = (pageIndex > -1) && (pageSize > -1) && (shopId > -1);
        if (temp) {
            long productCategoryId = HttpRequestServletUtil.getLong(request, "productCategoryId");
            /*获取模糊查询的名字*/
            String productName = HttpRequestServletUtil.getString(request, "productName");
            /*组合查询条件*/
            Product productCondition = compactProductConditionSearch(shopId, productCategoryId, productName);
            /*根据传入的查询条件返回商品列表以及总数*/
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("erMsg", "empty shopId or pageIndex or  pageSize");
        }
        return modelMap;
    }

    /**
     * 组合查询条件，将条件封装到compactProductConditionSearch中
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductConditionSearch(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            /*查询某个商品类别下的商品列表*/
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null) {
            /*查询名字里包含productName的店铺列表*/
            productCondition.setProductName(productName);
        }
        /*只允许状态为上架的商品*/
        productCondition.setEnableStatus(1);

        return productCondition;
    }
}
