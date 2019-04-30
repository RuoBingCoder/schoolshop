package com.shop.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.o2o.dto.ImgHolder;
import com.shop.o2o.dto.ShopExecution;
import com.shop.o2o.entity.Area;
import com.shop.o2o.entity.PersonInfo;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.entity.ShopCategory;
import com.shop.o2o.enums.ShopStateEnum;
import com.shop.o2o.exection.ShopOperationException;
import com.shop.o2o.service.AreaService;
import com.shop.o2o.service.ShopCategoryService;
import com.shop.o2o.service.ShopService;
import com.shop.o2o.util.CodeUtil;
import com.shop.o2o.util.HttpRequestServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * @author : 石建雷
 * @date :2019/4/10
 */
@Controller
@RequestMapping("/shopadmin")
@Slf4j
public class ShopManageController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    /**
     * 获取区域和类目列表，为前端做初始化
     *
     * @return
     */
    @GetMapping("/getshopinitinfo")
    @ResponseBody
    public Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> categoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            categoryList = shopCategoryService.shopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("categoryList", categoryList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);

            return modelMap;
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
    }

    /**
     * 注册店铺
     *
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/registerShop")
    @ResponseBody
    public Map<String, Object> registerShop(HttpServletRequest request) throws IOException {
        //接受并转换相应的参数，包括店铺信息以及图片信息

        Map<String, Object> stringObjectMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            stringObjectMap.put("success", false);
            stringObjectMap.put("msg", "请重新输入验证码！");
            return stringObjectMap;
        }
        String shopStr = HttpRequestServletUtil.getString(request, "shopStr");
        log.info("【shopStr:】={}", shopStr);
        //json处理
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            stringObjectMap.put("success", false);
            stringObjectMap.put("errMsg", e.getMessage());
            return stringObjectMap;
        }
        CommonsMultipartFile shopImg = null;

//        ImgHolder imgHolder = new ImgHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
        //创建一个通用的多部分解析器.
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求...
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            stringObjectMap.put("success", false);
            stringObjectMap.put("errMsg", "上传图片不能为空！");
            return stringObjectMap;

        }
        //  注册店铺
        if (shop != null && shopImg != null) {
//            session TODO
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ImgHolder imgHolder = new ImgHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
            ShopExecution shopException = shopService.shopAdd(shop, imgHolder);
            if (shopException.getState().equals(ShopStateEnum.CHECK.getCode())) {
                stringObjectMap.put("success", true);
                stringObjectMap.put("msg", "注册成功！");
                @SuppressWarnings("unchecked")
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if (shopList == null || shopList.size() == 0) {
                    shopList = new ArrayList<Shop>();
                }
                shopList.add(shopException.getShop());
                request.getSession().setAttribute("shopList", shopList);
                return stringObjectMap;

            } else {
                stringObjectMap.put("success", false);
                stringObjectMap.put("msg", "注册失败！");
                return stringObjectMap;
            }
        }
        //  返回注册信息
        return stringObjectMap;

    }

    /**
     * 通过店铺ID 获取店铺信息列表
     *
     * @param request
     * @return
     */

    @RequestMapping("/getshopid")
    @ResponseBody
    public Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Long shopId = HttpRequestServletUtil.getLong(request, "shopId");
        if (shopId > -1) {
            try {
                Shop shop = shopService.queryByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("msg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("msg", "empty shopId");

        }
        return modelMap;
    }

    /**
     * 修改店铺信息
     *
     * @param request
     * @return
     * @throws IOException
     */

    @PostMapping("/modifyShop")
    @ResponseBody
    public Map<String, Object> modifyShop(HttpServletRequest request) throws IOException {
        //接受并转换相应的参数，包括店铺信息以及图片信息

        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("msg", "请重新输入验证码！");
        }
        String shopStr = HttpRequestServletUtil.getString(request, "shopStr");

        //json处理
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());


        }

        CommonsMultipartFile shopImg = null;

        //创建一个通用的多部分解析器.
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求...
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }
        //  修改店铺
        if (shop != null && shop.getShopId() > 0) {
//            session com.sun.xml.internal.bind.v2.TODO
//            PersonInfo owner = new PersonInfo();
            PersonInfo personInfo = new PersonInfo();
            personInfo.setUserId(3L);
            shop.setOwner(personInfo);
            ShopExecution shopException;
            try {
                if (shopImg == null) {
                    shopException = shopService.shopModify(shop, null);
                } else {
                    ImgHolder imgHolder = new ImgHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
                    shopException = shopService.shopModify(shop, imgHolder);
                }
                if (shopException.getState().equals(ShopStateEnum.CHECK.getCode())) {
                    modelMap.put("success", true);
                    modelMap.put("msg", "修改成功！");
                } else {
                    modelMap.put("success", false);
                    modelMap.put("msg", "修改失败！");


                }

            } catch (ShopOperationException e) {
                modelMap.put("success", false);
                modelMap.put("msg", e.getMessage());

            }
        }

        return modelMap;
    }

    /**
     * session 做重定向
     *
     * @param request
     * @return
     */
    @GetMapping("/getshopmanagementinfo")
    @ResponseBody
    public Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        long shopId = HttpRequestServletUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {

                modelMap.put("redirect", true);
                modelMap.put("url", "/school_shop/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect", false);
                modelMap.put("shopId", currentShop.getShopId());
            }

        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect", false);
        }
        return modelMap;

    }

    /**
     * 获取店铺列表
     *
     * @param request
     * @return
     */
    @GetMapping("/getshoplist")
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
//        防止owner为空先进行初始化
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("test");
        request.getSession().setAttribute("user", user);
        user = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shop = new Shop();
            shop.setOwner(user);
            ShopExecution shopException = shopService.queryShopList(shop, 0, 100);
            modelMap.put("shopList", shopException.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("msg", e.getMessage());
        }
        return modelMap;
    }

}

