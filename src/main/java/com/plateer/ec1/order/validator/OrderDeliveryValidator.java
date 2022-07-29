package com.plateer.ec1.order.validator;

import com.plateer.ec1.order.vo.req.OrderRequestVO;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

public class OrderDeliveryValidator {

    public static Predicate<OrderRequestVO> eCouponParamPredicate = vo -> vo.getOrderDeliveryVOList().stream()
            .allMatch(e -> StringUtils.isNotEmpty(e.getRmtiHpNo()));

    public static Predicate<OrderRequestVO> generalParamPredicate = vo -> vo.getOrderDeliveryVOList().stream()
            .allMatch(e -> StringUtils.isNotEmpty(e.getRmtiNm())
                    && StringUtils.isNotEmpty(e.getRmtiHpNo())
                    && StringUtils.isNotEmpty(e.getRmtiAddr())
                    && StringUtils.isNotEmpty(e.getRmtiAddrDtl()
            ));

    public static Predicate<OrderRequestVO> eCoupondeliveryCntPredicate =  vo -> {
        int deliveryCnt = vo.getOrderDeliveryVOList().size();
        int orderPrdCnt = vo.getOrderProductVOList().size();
        return deliveryCnt == 1 || deliveryCnt == orderPrdCnt;
    };

}
