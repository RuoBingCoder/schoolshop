package com.shop.o2o.service;

import com.shop.o2o.dto.ImgHolder;
import com.shop.o2o.dto.ProductExecution;
import com.shop.o2o.entity.Product;
import com.shop.o2o.exection.ProductOperationException;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/19
 * 商品
 */
public interface ProductService {
    /**
     * 通过商品id查询唯一的商品信息
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 添加商品信息及图片处理
     *
     * @param product
     * @param imgHolder
     * @param imgHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImgHolder imgHolder, List<ImgHolder> imgHolderList) throws ProductOperationException;

    /**
     * 修改商品信息及图片处理
     *
     * @param product
     * @param imgHolder
     * @param imgHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product, ImgHolder imgHolder, List<ImgHolder> imgHolderList) throws ProductOperationException;


    ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
