package com.shop.o2o.service.impl;

import com.shop.o2o.dao.ProductCategoryDao;
import com.shop.o2o.dao.ProductDao;
import com.shop.o2o.dto.ProductCategoryExecution;
import com.shop.o2o.entity.ProductCategory;
import com.shop.o2o.enums.ProductCategoryStateEnum;
import com.shop.o2o.exection.ProductCategoryOperationException;
import com.shop.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : 石建雷
 * @date :2019/4/17
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    @Autowired
    private ProductCategoryDao productCategoryDao;
    @Autowired
    private ProductDao productDao;

    @Transactional(rollbackFor = Exception.class)
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryCategoryList(shopId);
    }

    /**
     * 批量添加商品类目
     *
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                int effectNum = productCategoryDao.productCategoryAddInBatches(productCategoryList);
                if (effectNum <= 0) {
                    throw new ProductCategoryOperationException("创建店铺失败！");
                } else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.INSERT_SUCCESS);
                }

            } catch (Exception e) {
                throw new ProductCategoryOperationException("productCategoryAddInBatches:error!" + e.getMessage());

            }
        } else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }

    }

    /**
     * 删除商品
     *
     * @param productCategoryId
     * @param shopId
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) {
        //解除product商品与ProductCategoryId的关联将此商品类别下的上的id设置为空
        try {
            int effectNum = productDao.updateProductCategoryToNull(productCategoryId);
            if (effectNum < 0) {
                throw new RuntimeException("更新商品失败！");
            }
        } catch (Exception e) {
            throw new RuntimeException("删除商品失败！" + e.getMessage());
        }

        try {
            int effectNum = productCategoryDao.deleteProductCategory(productCategoryId, shopId);
            if (effectNum <= 0) {
                throw new ProductCategoryOperationException("商品类别删除失败！");
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.DELETE_SUCCESS);
            }
        } catch (Exception e) {
            throw new ProductCategoryOperationException("delete error!" + e.getMessage());
        }
    }
}
