package com.plateer.ec1.order.validator;

import com.plateer.ec1.product.vo.ProductInfoVO;
import org.apache.commons.lang3.ObjectUtils;

import java.util.function.Predicate;

public class OrderProductValidator {

    public static Predicate<ProductInfoVO> isExistPrd = ObjectUtils::isNotEmpty;
    public static Predicate<ProductInfoVO> isSelling = ProductInfoVO::isSelling;
    public static Predicate<ProductInfoVO> isGeneralPrd = ProductInfoVO::isGeneralProduct;
    public static Predicate<ProductInfoVO> isECouponPrd = ProductInfoVO::isECouponProduct;
    public static Predicate<ProductInfoVO> isGeneralDelivery = ProductInfoVO::isGeneralDelivery;
    public static Predicate<ProductInfoVO> isECouponDelivery = ProductInfoVO::isECouponDelivery;

}
