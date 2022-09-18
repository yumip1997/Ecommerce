package com.plateer.ec1.order.validator;

import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class OrderDeliveryValidator {

    public static Consumer<OrderRequestVO> validateEcouponParam = (vo) -> {
        boolean isValid = vo.getOrderDeliveryVOList().stream()
                .allMatch(e -> StringUtils.isNotEmpty(e.getRmtiHpNo()));

        if(isValid) return;
        throw new ValidationException(OrderException.INVALID_ORDER.msg);
    };

    public static Consumer<OrderRequestVO> validateGeneralParam = (vo) -> {
        boolean isValid = vo.getOrderDeliveryVOList().stream()
                .allMatch(e -> StringUtils.isNotEmpty(e.getRmtiNm())
                        && StringUtils.isNotEmpty(e.getRmtiHpNo())
                        && StringUtils.isNotEmpty(e.getRmtiAddr())
                        && StringUtils.isNotEmpty(e.getRmtiAddrDtl()
                ));

        if(isValid) return;
        throw new ValidationException(OrderException.INVALID_ORDER.msg);
    };

    public static Consumer<OrderRequestVO> validateEcouponCnt = (vo) -> {
        int deliveryCnt = vo.getOrderDeliveryVOList().size();
        int orderPrdCnt = vo.getOrderProductVOList().size();
        boolean isValid = deliveryCnt == 1 || deliveryCnt == orderPrdCnt;

        if(isValid) return;
        throw new ValidationException(OrderException.INVALID_ORDER.msg);
    };
}
