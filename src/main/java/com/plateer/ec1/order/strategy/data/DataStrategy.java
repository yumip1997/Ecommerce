package com.plateer.ec1.order.strategy.data;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpDvpAreaInfo;
import com.plateer.ec1.common.model.order.OpGoodsInfo;
import com.plateer.ec1.common.model.order.OpOrdBase;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.vo.*;
import com.plateer.ec1.order.vo.req.OrderRequestVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public interface DataStrategy extends CustomFactory<OPT0001Code> {

    OrdClmCreationVO<OrderVO, Object> create(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList);

    default OpOrdBase createOpOrdBase(String ordNo, OrderBasicVO orderBasicVO){
        return orderBasicVO.toOpOrdBase(ordNo);
    }

    default List<OpGoodsInfo> createOpGoodsInfoList(String ordNo, List<OrderProductView> orderProductViewList){
        return orderProductViewList.stream()
                .map(e -> e.toOpGoodsInfo(ordNo))
                .collect(Collectors.toList());
    }

    default List<OpClmInfo> createOpClmInfoList(String ordNo, List<OrderDeliveryVO> orderDeliveryVOList){
        List<OpClmInfo> opClmInfoList = new ArrayList<>();

        for (OrderDeliveryVO orderDeliveryVO : orderDeliveryVOList) {
            List<OrderDeliveryGroupInfoVO> orderDeliveryGroupInfoVOList = orderDeliveryVO.getOrderDeliveryGroupInfoVOList();

            for (OrderDeliveryGroupInfoVO orderDeliveryGroupInfoVO : orderDeliveryGroupInfoVOList) {
                List<OrderProductVO> orderProductVOList = orderDeliveryGroupInfoVO.getOrderProductVOList();

                int ordSeq = 1;
                for (OrderProductVO orderProductVO : orderProductVOList) {
                    OpClmInfo opClmInfo = orderProductVO.toOpClmInfo(ordNo, ordSeq++, orderDeliveryGroupInfoVO.getDvGrpNo());
                    opClmInfoList.add(opClmInfo);
                }
            }
        }

        return opClmInfoList;
    }

    default List<OpDvpAreaInfo> createOpDvpAreaInfoList(String ordNo, List<OrderDeliveryVO> orderDeliveryVOList){
        return orderDeliveryVOList.stream()
                .map(e -> e.toOpDvpAreaInfo(ordNo))
                .collect(Collectors.toList());
    }


}
