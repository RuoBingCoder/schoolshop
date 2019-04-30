package com.shop.o2o.controller.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.o2o.dto.ImgHolder;
import com.shop.o2o.dto.ProductExecution;
import com.shop.o2o.entity.Product;
import com.shop.o2o.entity.ProductCategory;
import com.shop.o2o.entity.Shop;
import com.shop.o2o.enums.ProductStateEnum;
import com.shop.o2o.service.ProductCategoryService;
import com.shop.o2o.service.ProductService;
import com.shop.o2o.util.CodeUtil;
import com.shop.o2o.util.HttpRequestServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : 石建雷
 * @date :2019/4/20
 * 商品添加
 */
@RestController
@RequestMapping("/productmanagement")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;
    //    图片最大上传个数
    private static final int IMAGE_MAX_COUNT = 6;


    /**
     * 获取商品列表前端展示
     *
     * @param request
     * @return
     */
    @GetMapping("/getproductlistbyshop")
    public Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        /*获取前台传过来的页码*/
        int pageIndex = HttpRequestServletUtil.getInt(request, "pageIndex");
        /*获取前台传过来的每页要求商品数上限*/
        int pageSize = HttpRequestServletUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        /*空值判断*/
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            /*
             *获取传入的需要检索的条件，包括是否需要从某个商品类别以及模糊查找商品名去筛选某个店铺下的商品列表
             *筛选的条件可以进行排列组合
             */
            long productCategoryId = HttpRequestServletUtil.getLong(request, "productCategoryId");
            String productName = HttpRequestServletUtil.getString(request, "productName");
            Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
            //传入查询条件以及分页信息进行查询，返回相应商品列表以及总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId or pageIndex or pageSize!");
        }
        return modelMap;
    }

    /**
     * 通过商品id获取商品信息
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition(Long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        //若有指定类别的要求则添加进去
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        //若有商品名模糊查询的要求则添加进去
        if (productName != null) {
            productCondition.setProductName(productName);

        }
        return productCondition;
    }

    /**
     * 添加商品信息
     *
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/addproduct")
    public Map<String, Object> addProduct(HttpServletRequest request) throws IOException {

        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("msg", "请重新输入验证码！");
        }
        /*接受前端参数变量的初始化，包括上商品，缩略图，详情图列表实体类*/
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpRequestServletUtil.getString(request, "productStr");
        ImgHolder thumbnail = null;
        List<ImgHolder> productImgList = new ArrayList<ImgHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, productImgList);

            } else {
                modelMap.put("success", false);
                modelMap.put("Msg", "上传照片不能为空");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        //尝试获取前端传过来的表单string流并将其转换成Product实体类
        try {
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        //若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
//                执行添加操作
                ProductExecution productException = productService.addProduct(product, thumbnail, productImgList);
                if (productException.getStatus() == ProductStateEnum.SUCCESS.getCode()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productException.getStatus());

                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息！");
        }
        return modelMap;
    }

    /**
     * 获取修改商品信息为前端做初始化
     *
     * @param productId
     * @return
     */
    @RequestMapping("/getproductbyid")
    public Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productId > -1) {
            Product product = productService.getProductById(productId);
            List<ProductCategory> productCategoryList = productCategoryService.getProductCategoryList(product.getShop().getShopId());
            modelMap.put("product", product);
            modelMap.put("productCategoryList", productCategoryList);
            modelMap.put("success", true);
        } else {

            modelMap.put("success", false);
            modelMap.put("errMsg", "productId 为空！");


        }
        return modelMap;
    }

    /**
     * 修改商品信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/modifyproduct")
    public Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //是商品编辑时候调用还是上下架操作的时候调用
        //若为前者则进行验证码判断，后者则跳过验证码判断
        boolean statusChange = HttpRequestServletUtil.getBoolean(request, "statusChange");
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("msg", "请重新输入验证码！");
        }
        /*接受前端参数变量的初始化，包括上商品，缩略图，详情图列表实体类*/
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImgHolder thumbnail = null;
        List<ImgHolder> productImgList = new ArrayList<ImgHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        try {
            //若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, thumbnail, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("Msg", "上传照片不能为空");
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        //尝试获取前端传过来的表单string流并将其转换成Product实体类
        try {
            String productStr = HttpRequestServletUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        //若Product信息，缩略图以及详情图列表为非空，则开始进行商品添加操作
        if (product != null) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
//                开始进行商品变更操作
                ProductExecution productException = productService.modifyProduct(product, thumbnail, productImgList);
                if (productException.getStatus() == ProductStateEnum.SUCCESS.getCode()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", productException.getStatus());

                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息！");
        }
        return modelMap;
    }

    private ImgHolder handleImage(HttpServletRequest request, ImgHolder imgHolder, List<ImgHolder> productImageList) throws IOException {

        MultipartHttpServletRequest servletRequest = (MultipartHttpServletRequest) request;
        //取出缩略图并构建ImgHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) servletRequest.getFile("thumbnail");
        imgHolder = new ImgHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
        //取出详情图列表并构建List<ImgHolder>列表对象，最多支持六张图片上传
        for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
            CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile) servletRequest.getFile("productImg" + i);
            //若取出的第i个详情图片文件流不为空，则将其加入详情图列表
            if (commonsMultipartFile != null) {
                ImgHolder productImg = new ImgHolder(commonsMultipartFile.getOriginalFilename(), commonsMultipartFile.getInputStream());
                productImageList.add(productImg);
            } else {
                //若取出的第个详情图片文件流为空，则终止循环
                break;
            }
        }

        return imgHolder;
    }
}
