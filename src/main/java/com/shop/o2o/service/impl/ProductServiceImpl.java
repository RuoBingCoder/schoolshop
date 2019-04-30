package com.shop.o2o.service.impl;

import com.shop.o2o.dao.ProductDao;
import com.shop.o2o.dao.ProductImgDao;
import com.shop.o2o.dto.ImgHolder;
import com.shop.o2o.dto.ProductExecution;
import com.shop.o2o.entity.Product;
import com.shop.o2o.entity.ProductImg;
import com.shop.o2o.enums.ProductStateEnum;
import com.shop.o2o.exection.ProductOperationException;
import com.shop.o2o.service.ProductService;
import com.shop.o2o.util.ImgUtil;
import com.shop.o2o.util.PageCalculator;
import com.shop.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/19
 * 批量添加
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    /**
     * 1.处理缩略图，获取缩略图相对路径并赋值给product
     * 2.往tb_product写入商品信息，获取productld
     * 3.结合productId批量处理商品详情图
     * 4.将商品详情图列表批量插入tb_product_img中
     *
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductExecution addProduct(Product product, ImgHolder thumbnail, List<ImgHolder> productImgList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
//            给商品附上默认值
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
//            默认上架状态
            product.setEnableStatus(1);
            if (thumbnail != null) {

                addThumbnail(product, thumbnail);
            }
            try {
//                创建商品信息
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("创建商品失败！");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品失败" + e.toString());
            }
//            若商品详情不为空则添加
            if (productImgList != null && productImgList.size() > 0) {
                addProductImg(product, productImgList);


            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        }
        return new ProductExecution(ProductStateEnum.EMPTY);
    }

    /**
     * 更新商品信息
     *
     * @param product
     * @param imgHolder     缩略图
     * @param imgHolderList
     * @return
     * @throws ProductOperationException 1.若缩略图参数有值，则处理缩略图，
     *                                   若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
     *                                   2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
     *                                   3.将tb_product_img下面的该商品原先的商品详情图记录全部清除
     *                                   4.更新to_product_img以及tb_product的信息
     */
    @Override
    public ProductExecution modifyProduct(Product product, ImgHolder imgHolder, List<ImgHolder> imgHolderList) throws ProductOperationException {
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(new Date());
            //若商品缩略图不为空且原有缩略图不为空则删除原有缩略图并添加
            if (imgHolder != null) {
                //先获取一遍原有信息，因为原来的信息里有原图片地址
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null) {
                    ImgUtil.deleteImgPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, imgHolder);
            }
            //如果有新存入的商品详情图，则将原先的删除，并添加新的图片
            if (imgHolderList != null && imgHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImg(product, imgHolderList);
            }
            try {
//                更新商品信息
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0) {
                    throw new ProductOperationException("更新商品失败！");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                throw new ProductOperationException("更新商品失败" + e.toString());
            }

        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 获取商品列表，并分页
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    /**
     * 删除某个商品下的所有详情图
     *
     * @param productId
     */
    private void deleteProductImgList(long productId) {
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
//        删掉原来的图片
        for (ProductImg productImg :
                productImgList) {
            ImgUtil.deleteImgPath(productImg.getImgAddr());

        }
        productImgDao.deleteProductImgByProductId(productId);
    }

    /**
     * 添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImgHolder thumbnail) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        String thumbnailAddr = ImgUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);

    }

    private void addProductImg(Product product, List<ImgHolder> productImgHolder) {
        String dest = PathUtil.getShopImgPath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
//        遍历一遍图片一次去处理，并添加ProductImg实体类中
        for (ImgHolder imgHolder : productImgHolder) {

            String imgAddr = ImgUtil.generateThumbnail(imgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
//        确实有图片进行处理的，则进行批量添加
        if (productImgList.size() > 0) {
            try {
                int batchAddProductImg = productImgDao.batchInsertProductImg(productImgList);
                if (batchAddProductImg <= 0) {

                    throw new ProductOperationException("图片添加失败！");
                }
            } catch (Exception e) {
                throw new ProductOperationException("图片添加失败" + e.toString());
            }
        }

    }

}
