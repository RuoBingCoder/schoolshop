package com.shop.o2o.controller.shopadmin;

import com.shop.o2o.dto.ProductCategoryExecution;
import com.shop.o2o.dto.Result;
import com.shop.o2o.entity.ProductCategory;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.enums.ProductCategoryStateEnum;
import com.shop.o2o.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 石建雷
 * @date :2019/4/17
 */
@Controller
@Slf4j
@RequestMapping("/productshopcategory")
public class ProductShopcategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 获取商品类目列表
     *
     * @param request
     * @return
     */
    @GetMapping("/getproductcatugorylist")
    @ResponseBody
    public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {

        Shop shop = new Shop();
        shop.setShopId(3L);
        request.getSession().setAttribute("currentShop", shop);
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> productCategoryList = null;

        if (currentShop != null && currentShop.getShopId() > 0) {
            productCategoryList = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, productCategoryList);
        } else {
            ProductCategoryStateEnum stateEnum = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, stateEnum.getCode(), stateEnum.getMsg());
        }
    }

    /**
     * 批量添加商品类目
     *
     * @param productCategorielList
     * @param request
     * @return
     */

    @PostMapping("/addbatchproductcategory")
    @ResponseBody
    public Map<String, Object> addBatchProductCategory(@RequestBody List<ProductCategory> productCategorielList, HttpServletRequest request) {
        Map<String, Object> modleMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory category : productCategorielList) {
            category.setShopId(currentShop.getShopId());

        }
        if (productCategorielList.size() > 0) {
            try {
                ProductCategoryExecution productCategoryException = productCategoryService.batchAddProductCategory(productCategorielList);
                if (productCategoryException.getCode() == ProductCategoryStateEnum.INSERT_SUCCESS.getCode()) {
                    modleMap.put("success", true);

                } else {
                    modleMap.put("success", false);
                    modleMap.put("msg", productCategoryException.getMsg());
                }
            } catch (RuntimeException e) {
                modleMap.put("success", false);
                modleMap.put("msg", e.toString());
                return modleMap;

            }

        } else {
            modleMap.put("success", false);
            modleMap.put("msg", "请至少输入一个字符！");
        }
        return modleMap;
    }

    @PostMapping("/deleteproduct")
    @ResponseBody
    public Map<String, Object> deleteProduct(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        log.info("========productCategoryId========{}", productCategoryId);
        if (productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution productCategoryException = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                if (productCategoryException.getCode() == ProductCategoryStateEnum.DELETE_SUCCESS.getCode()) {
                    modelMap.put("success", true);

                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", productCategoryException.getMsg());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("msg", e.toString());
                return modelMap;

            }
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "请至少选一个商品类别！");
        }
        return modelMap;
    }

}
