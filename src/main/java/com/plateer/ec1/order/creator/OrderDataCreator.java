package com.plateer.ec1.order.creator;

import com.plateer.ec1.common.excpetion.custom.DataCreationException;
import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.delivery.enums.DVP0001Code;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.order.enums.OrderException;
import com.plateer.ec1.order.mapper.OrderMapper;
import com.plateer.ec1.order.vo.*;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class OrderDataCreator {

    private final OrderMapper orderMapper;

    public static OrderDataCreator of(OrderMapper orderMapper){
        return new OrderDataCreator(orderMapper);
    }

    public OrdClmCreationVO<OrderVO, Object> create(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        try{
            OrderRequestVO cloneReq = orderRequestVO.clone();
            OrdClmCreationVO<OrderVO, Object> creationVO = new OrdClmCreationVO<>();
            creationVO.setOrdNo(cloneReq.getOrdNo());
            creationVO.setInsertData(createOrderVO(cloneReq, orderProductViewList));
            return creationVO;
        }catch (Exception e){
            throw new DataCreationException(OrderException.DATA_CREATION_ERROR.msg);
        }
    }

    private OrderVO createOrderVO(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList){
        String ordNo = orderRequestVO.getOrdNo();

        OrderVO orderVO = new OrderVO();
        orderVO.setOpOrdBase(createOpOrdBase(orderRequestVO));
        orderVO.setOpGoodsInfoList(createOpGoodsInfoList(ordNo, orderProductViewList));
        orderVO.setOpClmInfoList(createOpClmInfoList(orderRequestVO));
        orderVO.setOpDvpAreaInfoList(createOpDvpAreaInfoList(ordNo, orderRequestVO.getOrderDeliveryVOList()));
        orderVO.setOpDvpInfoList(createOpDvpInfoList(orderRequestVO));
        orderVO.setOpOrdCostInfoList(createOpOrdCostInfoList(orderRequestVO));
        orderVO.setOpOrdBnfInfoList(createOpOrdBnfInfoList(orderRequestVO));
        orderVO.setOpOrdBnfRelInfoList(createOpOrdBnfRelInfoList(orderRequestVO, orderVO));
        return orderVO;
    }

    private OpOrdBase createOpOrdBase(OrderRequestVO orderRequestVO){
        OrderBasicVO orderBasicVO = orderRequestVO.getOrderBasicVO();
        OpOrdBase opOrdBase = orderBasicVO.toOpOrdBase(orderRequestVO.getOrdNo());
        opOrdBase.setOrdCmtDtime(orderRequestVO.isContainsVacctPayment() ? null : LocalDateTime.now());
        return opOrdBase;
    }

    private List<OpGoodsInfo> createOpGoodsInfoList(String ordNo, List<OrderProductView> orderProductViewList){
        return orderProductViewList.stream()
                .map(e -> e.toOpGoodsInfo(ordNo))
                .collect(Collectors.toList());
    }

    private List<OpClmInfo> createOpClmInfoList(OrderRequestVO orderRequestVO){
        List<OpClmInfo> collect = orderRequestVO.getOrderDeliveryVOList().stream()
                .flatMap(e -> e.getOrderDeliveryGroupInfoVOList().stream())
                .flatMap(e -> e.toOpClmInfoList(orderRequestVO.getOrdNo(), orderRequestVO.getOrderProductVOMap()).stream())
                .collect(Collectors.toList());
        return setUpOpClmInfoList(orderRequestVO, collect);
    }

    private List<OpClmInfo> setUpOpClmInfoList(OrderRequestVO orderRequestVO, List<OpClmInfo> opClmInfoList){
        boolean containsVacctPayment = orderRequestVO.isContainsVacctPayment();
        int ordSeq = 1;
        for (OpClmInfo opClmInfo : opClmInfoList) {
            opClmInfo.setOrdSeq(ordSeq++);
            opClmInfo.setOrdPrgsScd(containsVacctPayment ? OPT0004Code.ORDER_WAITING.getCode() : OPT0004Code.ORDER_COMPLETE.getCode());
            opClmInfo.setOrdClmCmtDtime(containsVacctPayment ? null : LocalDateTime.now());
        }
        return opClmInfoList;
    }

    private List<OpDvpAreaInfo> createOpDvpAreaInfoList(String ordNo, List<OrderDeliveryVO> orderDeliveryVOList){
        return orderDeliveryVOList.stream()
                .map(e -> e.toOpDvpAreaInfo(ordNo))
                .collect(Collectors.toList());
    }

    private List<OpDvpInfo> createOpDvpInfoList(OrderRequestVO orderRequestVO){
        OrderBasicVO orderBasicVO = orderRequestVO.getOrderBasicVO();
        String dvMthdCd = OPT0001Code.of(orderBasicVO.getOrdTpCd()) == OPT0001Code.GENERAL ? DVP0001Code.DELIVERY.getCode() : DVP0001Code.NON_DELIVERY.getCode();

        return orderRequestVO.getOrderDeliveryVOList().stream()
                .flatMap(e -> e.toOpDvpInfoList(orderRequestVO.getOrdNo(), dvMthdCd).stream())
                .collect(Collectors.toList());
    }

    private List<OpOrdCostInfo> createOpOrdCostInfoList(OrderRequestVO orderRequestVO){
        return orderRequestVO.getOrderDeliveryVOList().stream()
                .flatMap(e -> e.getOrderDeliveryGroupInfoVOList().stream())
                .flatMap(e -> e.toOpOrdCostInfoList(orderRequestVO.getOrdNo()).stream())
                .collect(Collectors.toList());
    }

    private List<OpOrdBnfInfo> createOpOrdBnfInfoList(OrderRequestVO orderRequestVO){
        List<OpOrdBnfInfo> opOrdBnfInfoList = new ArrayList<>();
        opOrdBnfInfoList.addAll(createPrdBnfInfoList(orderRequestVO.getOrderProductVOList()));
        opOrdBnfInfoList.addAll(createCartBnfInoList(orderRequestVO));
        return opOrdBnfInfoList;
    }

    private List<OpOrdBnfInfo> createPrdBnfInfoList(List<OrderProductVO> orderProductVOList){
        return orderProductVOList.stream()
                .flatMap(e -> e.toPrdBnfInoList(orderMapper::getBnfNo).stream())
                .collect(Collectors.toList());
    }

    private List<OpOrdBnfInfo> createCartBnfInoList(OrderRequestVO orderRequestVO){
        return orderRequestVO.toCartBnfInoList(orderMapper::getBnfNo);
    }

    private List<OpOrdBnfRelInfo> createOpOrdBnfRelInfoList(OrderRequestVO orderRequestVO, OrderVO orderVO){
        if(CollectionUtils.isEmpty(orderVO.getOpOrdBnfInfoList())) return Collections.emptyList();

        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = new ArrayList<>();
        opOrdBnfRelInfoList.addAll(createPrdBnfRelInfoList(orderRequestVO.getOrderProductVOList(), orderVO));
        opOrdBnfRelInfoList.addAll(createCartBnfRelInfoList(orderRequestVO, orderVO));
        return opOrdBnfRelInfoList;
    }

    private List<OpOrdBnfRelInfo> createPrdBnfRelInfoList(List<OrderProductVO> orderProductVOList, OrderVO orderVO){
        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = new ArrayList<>();

        for (OrderProductVO orderProductVO : orderProductVOList) {
            List<OrderBenefitBaseVO> productBenefits = orderProductVO.getProductBenefits();
            if(CollectionUtils.isEmpty(productBenefits)) continue;

            String goodsNoItemNo = orderProductVO.getGoodsNoItemNo();
            for (OrderBenefitBaseVO productBenefit : productBenefits) {
                OpOrdBnfRelInfo opOrdBnfRelInfo = productBenefit.toOpOrdBnfRelInfo(
                        orderVO.getOrdNoFromOrdClmMap(goodsNoItemNo),
                        orderVO.getOrdSeqFromOrdClmMap(goodsNoItemNo),
                        orderVO.getOrdBnfFromOrdBnfMap(productBenefit.getCpnIssNo()));
                opOrdBnfRelInfoList.add(opOrdBnfRelInfo);
            }
        }

        return opOrdBnfRelInfoList;
    }

    private List<OpOrdBnfRelInfo> createCartBnfRelInfoList(OrderRequestVO orderRequestVO, OrderVO orderVO){
        if(CollectionUtils.isEmpty(orderRequestVO.getOrderBenefitVOList())) return Collections.emptyList();

        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = new ArrayList<>();

        for (OrderBenefitVO orderBenefitVO : orderRequestVO.getOrderBenefitVOList()) {

            List<OrderProductBaseVO> orderProductBaseVOList = orderBenefitVO.getOrderProductBaseVOList();
            long totalPrdBnfAplyOrdPrc = orderRequestVO.getTotalPrdBnfAplyOrdPrc(orderProductBaseVOList);

            for (OrderProductBaseVO orderProductBaseVO : orderProductBaseVOList) {
                String goodsNoItemNo = orderProductBaseVO.getGoodsNoItemNo();
                OrderProductVO orderProductVO = orderRequestVO.getOrderProductVOFromMap(goodsNoItemNo);
                long distributeAplyAmt = orderBenefitVO.distributeAplyAmt(orderProductVO.getPrdBnfAplyOrdPrc(), totalPrdBnfAplyOrdPrc);

                OpOrdBnfRelInfo opOrdBnfRelInfo = new OpOrdBnfRelInfo();
                opOrdBnfRelInfo.setOrdNo(orderVO.getOrdNoFromOrdClmMap(goodsNoItemNo));
                opOrdBnfRelInfo.setOrdBnfNo(orderVO.getOrdBnfFromOrdBnfMap(orderBenefitVO.getCpnIssNo()));
                opOrdBnfRelInfo.setOrdSeq(orderVO.getOrdSeqFromOrdClmMap(goodsNoItemNo));
                opOrdBnfRelInfo.setProcSeq(1);
                opOrdBnfRelInfo.setAplyCnclCcd(OPT0005Code.APPLY.getCode());
                opOrdBnfRelInfo.setAplyAmt(distributeAplyAmt);

                opOrdBnfRelInfoList.add(opOrdBnfRelInfo);
            }
        }
        return opOrdBnfRelInfoList;
    }

}
