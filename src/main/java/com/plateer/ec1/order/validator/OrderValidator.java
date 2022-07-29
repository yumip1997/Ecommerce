package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.enums.SystemType;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import com.plateer.ec1.product.vo.ProductInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public enum OrderValidator {

    FO_GENERAL(SystemType.FO.getCode(), OPT0001Code.GENERAL.getCode(),
            OrderProductValidator.isExistPrd
                    .and(OrderProductValidator.isSelling)
                    .and(OrderProductValidator.isGeneralPrd)
                    .and(OrderProductValidator.isGeneralDelivery),
            OrderDeliveryValidator.generalParamPredicate
    ),
    BO_GENERAL(SystemType.BO.getCode(), OPT0001Code.GENERAL.getCode(),
            OrderProductValidator.isExistPrd
                    .and(OrderProductValidator.isSelling)
                    .and(OrderProductValidator.isGeneralPrd)
                    .and(OrderProductValidator.isGeneralDelivery),
            OrderDeliveryValidator.generalParamPredicate),
    FO_ECOUPON(SystemType.FO.getCode(), OPT0001Code.ECOUPON.getCode(),
            OrderProductValidator.isExistPrd
                    .and(OrderProductValidator.isSelling)
                    .and(OrderProductValidator.isECouponPrd)
                    .and(OrderProductValidator.isECouponDelivery),
            OrderDeliveryValidator.generalParamPredicate
                    .and(OrderDeliveryValidator.eCouponParamPredicate)
                    .and(OrderDeliveryValidator.eCoupondeliveryCntPredicate)),
    BO_ECOUPON(SystemType.BO.getCode(), OPT0001Code.ECOUPON.getCode(),
            OrderProductValidator.isExistPrd
                    .and(OrderProductValidator.isSelling)
                    .and(OrderProductValidator.isECouponPrd)
                    .and(OrderProductValidator.isECouponDelivery),
            OrderDeliveryValidator.generalParamPredicate
                    .and(OrderDeliveryValidator.eCouponParamPredicate)
                    .and(OrderDeliveryValidator.eCoupondeliveryCntPredicate));

    private final String systemTypeCode;
    private final String orderTypeCode;
    private final Predicate<ProductInfoVO> productInfoVOPredicate;
    private final Predicate<OrderRequestVO> deliveryPredicate;

    public void isValid(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        isValidDelivery(orderRequestVO);
        isValidOrdPrd(orderProductViewList);
    }

    private void isValidOrdPrd(List<OrderProductView> orderProductViewList){
        for (OrderProductView orderProductView : orderProductViewList) {
            boolean isValid = productInfoVOPredicate.test(orderProductView.getProductInfoVO());
            if(isValid) continue;

            throw new ValidationException(OrderException.INVALID_ORDER.msg);
        }
    }

    private void isValidDelivery(OrderRequestVO orderRequestVO){
        boolean isValid = deliveryPredicate.test(orderRequestVO);
        if(!isValid){
            throw new ValidationException(OrderException.INVALID_ORDER.msg);
        }
    }

    public static OrderValidator findOrderValidator(OrderRequestVO orderRequestVO){
        return Arrays.stream(OrderValidator.values())
                .filter(orderValidator -> isEqualsTypeCode(orderValidator, orderRequestVO))
                .findFirst()
                .orElseThrow(() -> new ValidationException(OrderException.NOT_FIND_VALIDATOR.msg));
    }

    private static boolean isEqualsTypeCode(OrderValidator orderValidator, OrderRequestVO orderRequestVO){
        return orderValidator.systemTypeCode.equals(orderRequestVO.getSystemType()) && orderValidator.orderTypeCode.equals(orderRequestVO.getOrderType());
    }

}
