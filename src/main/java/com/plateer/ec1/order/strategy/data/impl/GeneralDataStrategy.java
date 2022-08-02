package com.plateer.ec1.order.strategy.data.impl;

import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.strategy.data.DataStrategy;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GeneralDataStrategy implements DataStrategy {

    @Override
    public OPT0001Code getType() {
        return OPT0001Code.GENERAL;
    }

    @Override
    public OrdClmCreationVO<OrderVO, Object> create(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        OrdClmCreationVO<OrderVO, Object> creationVO = new OrdClmCreationVO<>();
        creationVO.setOrdNo(orderRequestVO.getOrdNo());
        creationVO.setInsertData(createOrderVO(orderRequestVO, orderProductViewList));
        return creationVO;
    }

    private OrderVO createOrderVO(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList){
        String ordNo = orderRequestVO.getOrdNo();

        OrderVO orderVO = new OrderVO();
        orderVO.setOpOrdBase(createOpOrdBase(ordNo, orderRequestVO.getOrderBasicVO()));
        orderVO.setOpGoodsInfoList(createOpGoodsInfoList(ordNo, orderProductViewList));
        orderVO.setOpClmInfoList(createOpClmInfoList(ordNo, orderRequestVO.getOrderDeliveryVOList()));
        orderVO.setOpDvpAreaInfoList(createOpDvpAreaInfoList(ordNo, orderRequestVO.getOrderDeliveryVOList()));
        return orderVO;
    }
}
