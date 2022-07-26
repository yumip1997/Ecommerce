package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.vo.OrderRequestVO;
import com.plateer.ec1.order.vo.OrderValidationVO;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.function.Predicate;

@RequiredArgsConstructor
public enum OrderValidator {

    FO_GENERAL(SystemType.FO.getCode(), OrderType.GENERAL.getCode(),
            OrderCommonValidator.isSellingProudct
                    .and(OrderProductValidator.validateMaxPurchaseCnt)
                    .and(OrderProductValidator.validateMinPurchaseCnt)
                    .and(OrderTypeValidator.isGeneralOrderAbleProduct)
                    .and(OrderSystemTypeValidator.isFOOrderAbleProduct)
    ),
    BO_GENERAL(SystemType.BO.getCode(), OrderType.GENERAL.getCode(),
            OrderCommonValidator.isSellingProudct
                    .and(OrderProductValidator.validateMaxPurchaseCnt)
                    .and(OrderProductValidator.validateMinPurchaseCnt)
                    .and(OrderTypeValidator.isGeneralOrderAbleProduct)
                    .and(OrderSystemTypeValidator.isBOOrderAbleProduct)
    ),
    FO_ECOUPON(SystemType.FO.getCode(), OrderType.ECOUPON.getCode(),
            OrderCommonValidator.isSellingProudct
                    .and(OrderProductValidator.validateMaxPurchaseCnt)
                    .and(OrderProductValidator.validateMinPurchaseCnt)
                    .and(OrderTypeValidator.isEcouponOrderAbleProduct)
                    .and(OrderSystemTypeValidator.isFOOrderAbleProduct)
    ),
    BO_ECOUPON(SystemType.BO.getCode(), OrderType.ECOUPON.getCode(),
            OrderCommonValidator.isSellingProudct
                    .and(OrderProductValidator.validateMaxPurchaseCnt)
                    .and(OrderProductValidator.validateMinPurchaseCnt)
                    .and(OrderTypeValidator.isEcouponOrderAbleProduct)
                    .and(OrderSystemTypeValidator.isBOOrderAbleProduct)
    );

    private final String systemTypeCode;
    private final String orderTypeCode;
    private final Predicate<OrderValidationVO> predicate;

    public boolean test(OrderValidationVO s) {
        return predicate.test(s);
    }

    public static OrderValidator findOrderValidator(OrderRequestVO orderRequestVO){
        return Arrays.stream(OrderValidator.values())
                .filter(orderValidator -> isEqualsTypeCode(orderValidator, orderRequestVO))
                .findFirst()
                .orElseThrow(() -> new BusinessException(OrderException.NOT_FIND_VALIDATOR.msg));
    }

    private static boolean isEqualsTypeCode(OrderValidator orderValidator, OrderRequestVO orderRequestVO){
        return orderValidator.systemTypeCode.equals(orderRequestVO.getSystemType()) && orderValidator.orderTypeCode.equals(orderRequestVO.getOrderType());
    }

}
