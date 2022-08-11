package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.product.vo.ProductInfoVO;

import java.util.function.Consumer;

public class OrderProductValidator {

    public static Consumer<ProductInfoVO> validateExistPrd = (productInfoVO) -> {
        if (productInfoVO != null) return;
        throw new ValidationException(OrderException.NOT_EXIST_PRODUCT.msg);
    };

    public static Consumer<ProductInfoVO> validateIsSelling = (productInfoVO) -> {
        if (productInfoVO.isSelling()) return;
        throw new ValidationException(OrderException.INVALID_SELLING.msg);
    };

    public static Consumer<ProductInfoVO> validateGeneralPrd = (productInfoVO) -> {
        if (productInfoVO.isGeneralProduct()) return;
        throw new ValidationException(OrderException.NOT_GENERAL_PRODUCT_TYPE.msg);
    };

    public static Consumer<ProductInfoVO> validateECouponPrd = (productInfoVO) -> {
        if (productInfoVO.isECouponProduct()) return;
        throw new ValidationException(OrderException.NOT_ECOUPON_PRODUCT_TYPE.msg);
    };

    public static Consumer<ProductInfoVO> validateGeneralDelivery = (productInfoVO) -> {
        if (productInfoVO.isGeneralDelivery()) return;
        throw new ValidationException(OrderException.NOT_GENERAL_DELIVERY.msg);
    };

    public static Consumer<ProductInfoVO> validateECouponDelivery = (productInfoVO) -> {
        if (productInfoVO.isECouponDelivery()) return;
        throw new ValidationException(OrderException.NOT_ECOUPON_DELIVERY.msg);
    };


}
