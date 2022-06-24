package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.enums.OrderType;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.vo.OrderRequest;
import com.plateer.ec1.order.vo.OrderValidationDto;
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
    private final Predicate<OrderValidationDto> predicate;

    public boolean test(OrderValidationDto s) {
        return predicate.test(s);
    }

    public static OrderValidator findOrderValidatory(OrderRequest orderRequest) throws Exception {
        return Arrays.stream(OrderValidator.values())
                .filter(orderValidator -> isEqualsTypeCode(orderValidator, orderRequest))
                .findFirst()
                .orElseThrow(() -> new Exception(OrderException.NOT_FIND_VALIDATOR.msg));
    }

    private static boolean isEqualsTypeCode(OrderValidator orderValidator, OrderRequest orderRequest){
        return orderValidator.systemTypeCode.equals(orderRequest.getSystemType()) && orderValidator.orderTypeCode.equals(orderRequest.getOrderType());
    }

}
