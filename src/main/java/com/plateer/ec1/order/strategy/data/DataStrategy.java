package com.plateer.ec1.order.strategy.data;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.order.OpGoodsInfo;
import com.plateer.ec1.common.model.order.OpOrdBase;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderProductView;
import com.plateer.ec1.order.vo.OrderVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;

import java.util.ArrayList;
import java.util.List;

public interface DataStrategy extends CustomFactory<OPT0001Code> {

    OrdClmCreationVO<OrderVO, Object> create(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList);

    default OpOrdBase createOpOrdBase(String ordNo, OrderBasicVO orderBasicVO){
        return orderBasicVO.toOpOrdBase(ordNo);
    }

    default List<OpGoodsInfo> createOpGoodsInfoList(String ordNo, List<OrderProductView> orderProductViewList){
        List<OpGoodsInfo> opGoodsInfoList= new ArrayList<>();
        for (OrderProductView orderProductView : orderProductViewList) {
            OpGoodsInfo opGoodsInfo = orderProductView.toOpGoodsInfo(ordNo);
            opGoodsInfoList.add(opGoodsInfo);
        }
        return opGoodsInfoList;
    }

}
