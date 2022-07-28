package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.OrderProductViewVO;
import com.plateer.ec1.product.vo.ProductInfoVO;

import java.util.function.Predicate;

public class OrderTypeValidator {

    public static Predicate<OrderProductViewVO> isGeneralOrderAbleProduct = vo -> vo.getProductInfoVOList().stream().allMatch(ProductInfoVO::isGeneralProduct);
    public static Predicate<OrderProductViewVO> isGeneralDelivery = vo -> vo.getProductInfoVOList().stream().allMatch(ProductInfoVO::isGeneralDelivery);

    public static Predicate<OrderProductViewVO> isEcouponOrderAbleProduct = vo -> vo.getProductInfoVOList().stream().allMatch(ProductInfoVO::isECouponProduct);

}
