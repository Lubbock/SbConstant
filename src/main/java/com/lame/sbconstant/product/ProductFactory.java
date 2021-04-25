package com.lame.sbconstant.product;

import com.lame.sbconstant.detect.vo.FileType;
import com.lame.sbconstant.product.strategy.CommonProductStrategy;
import com.lame.sbconstant.product.strategy.EntityProductStrategy;

/**
 * 常量工厂
 **/
public class ProductFactory {

    public static ProductContext getProductContext(FileType fileType) {
        ProductContext productContext = null;
        switch (fileType) {
            case ENTITY:
                productContext = new ProductContext(new EntityProductStrategy());
                break;
            case DAO:
            case SERVICE:
            case COMMON:
            default:
                productContext = new ProductContext(new CommonProductStrategy());
                break;
        }
        return productContext;
    }
}
