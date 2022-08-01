package com.plateer.ec1.order.strategy.data.impl;

import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.extern.log4j.Log4j2;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Log4j2
public class GeneralDataStrategy implements DataStrategy {

    @Override
    public OPT0001Code getType() {
        return OPT0001Code.GENERAL;
    }

    @Override
    public OrdClmCreationVO<OrderVO, Object> create(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        return new OrdClmCreationVO<>();
    }
}
