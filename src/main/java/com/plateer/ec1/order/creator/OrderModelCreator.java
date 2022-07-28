package com.plateer.ec1.order.creator;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpGoodsInfo;
import com.plateer.ec1.common.model.order.OrderBase;
import com.plateer.ec1.order.vo.OrderProductViewVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;

public class OrderModelCreator {

    public static OrderBase commonOrderBase(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO){
        return null;
    }

    public static OpGoodsInfo commonOrderProduct(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO){
        return null;
    }

    public static OpClmInfo commonOrderClaim(OrderRequestVO orderRequestVO, OrderProductViewVO orderProductViewVO){
        return null;
    }
}
