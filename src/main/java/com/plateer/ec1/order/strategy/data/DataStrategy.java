package com.plateer.ec1.order.strategy.data;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.delivery.enums.DVP0001Code;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.vo.*;
import com.plateer.ec1.order.vo.req.OrderRequestVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//TODO 삭제 예정
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

    default List<OpClmInfo> createOpClmInfoList(OrderRequestVO orderRequestVO){
        List<OpClmInfo> opClmInfoList = new ArrayList<>();
        boolean containsVacctPayment = orderRequestVO.isContainsVacctPayment();

        List<OrderDeliveryVO> orderDeliveryVOList = orderRequestVO.getOrderDeliveryVOList();
        for (OrderDeliveryVO orderDeliveryVO : orderDeliveryVOList) {
            List<OrderDeliveryGroupInfoVO> orderDeliveryGroupInfoVOList = orderDeliveryVO.getOrderDeliveryGroupInfoVOList();

            for (OrderDeliveryGroupInfoVO orderDeliveryGroupInfoVO : orderDeliveryGroupInfoVOList) {
                List<OrderProductVO> orderProductVOList = orderDeliveryGroupInfoVO.getOrderProductVOList();

                int ordSeq = 1;
                for (OrderProductVO orderProductVO : orderProductVOList) {
                    OpClmInfo opClmInfo = orderProductVO.toOpClmInfo(orderRequestVO.getOrdNo(), ordSeq++, orderDeliveryGroupInfoVO.getDvGrpNo());
                    opClmInfo.setOrdPrgsScd(containsVacctPayment ? OPT0004Code.ORDER_WAITING.getCode() : OPT0004Code.ORDER_COMPLETE.getCode());
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

    default List<OpDvpInfo> createOpDvpInfoList(String ordNo, OrderRequestVO orderRequestVO){
        List<OpDvpInfo> opDvpInfoList = new ArrayList<>();

        OrderBasicVO orderBasicVO = orderRequestVO.getOrderBasicVO();
        String dvMthdCd = OPT0001Code.of(orderBasicVO.getOrdTpCd()) == OPT0001Code.GENERAL ? DVP0001Code.DELIVERY.getCode() : DVP0001Code.NON_DELIVERY.getCode();

        List<OrderDeliveryVO> orderDeliveryVOList = orderRequestVO.getOrderDeliveryVOList();
        for (OrderDeliveryVO orderDeliveryVO : orderDeliveryVOList) {

            List<OrderDeliveryGroupInfoVO> orderDeliveryGroupInfoVOList = orderDeliveryVO.getOrderDeliveryGroupInfoVOList();
            for (OrderDeliveryGroupInfoVO orderDeliveryGroupInfoVO : orderDeliveryGroupInfoVOList) {
                OpDvpInfo opDvpInfo = orderDeliveryGroupInfoVO.toOpDvpInfo(ordNo, orderDeliveryVO.getDvpSeq(), dvMthdCd);
                opDvpInfoList.add(opDvpInfo);
            }
        }

        return opDvpInfoList;
    }
}
