package com.plateer.ec1.order.strategy.data.impl;

import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.OrderProductViewVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ECouponDataStrategy implements DataStrategy {

    @Override
    public OrderVO create(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO) {
        log.info("이쿠폰 주문 데이터를 생성한다.");
        return null;
    }

    @Override
    public OPT0001Code getType() {
        return OPT0001Code.ECOUPON;
    }
}
