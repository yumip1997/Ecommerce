package com.plateer.ec1.order.creator;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.delivery.enums.DVP0001Code;
import com.plateer.ec1.order.enums.OPT0001Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.mapper.OrderMapper;
import com.plateer.ec1.order.vo.*;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderProductBaseVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderDataCreator {

    private final OrderMapper orderMapper;

    public OrdClmCreationVO<OrderVO, Object> create(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList) {
        OrdClmCreationVO<OrderVO, Object> creationVO = new OrdClmCreationVO<>();
        creationVO.setOrdNo(orderRequestVO.getOrdNo());
        creationVO.setInsertData(createOrderVO(orderRequestVO, orderProductViewList));
        return creationVO;
    }

    private OrderVO createOrderVO(OrderRequestVO orderRequestVO, List<OrderProductView> orderProductViewList){
        String ordNo = orderRequestVO.getOrdNo();

        OrderVO orderVO = new OrderVO();
        orderVO.setOpOrdBase(createOpOrdBase(orderRequestVO));
        orderVO.setOpGoodsInfoList(createOpGoodsInfoList(ordNo, orderProductViewList));
        orderVO.setOpClmInfoList(createOpClmInfoList(orderRequestVO));
        orderVO.setOpDvpAreaInfoList(createOpDvpAreaInfoList(ordNo, orderRequestVO.getOrderDeliveryVOList()));
        orderVO.setOpDvpInfoList(createOpDvpInfoList(orderRequestVO));
        orderVO.setOpOrdBnfInfoList(createOpOrdBnfInfoList(orderRequestVO));

        orderVO.setOpOrdBnfRelInfoList(createOpOrdBnfRelInfoList(orderRequestVO, orderVO.getOpClmInfoList(), orderVO.getOpOrdBnfInfoList()));
        return orderVO;
    }

    private OpOrdBase createOpOrdBase(OrderRequestVO orderRequestVO){
        OrderBasicVO orderBasicVO = orderRequestVO.getOrderBasicVO();
        return orderBasicVO.toOpOrdBase(orderRequestVO.getOrdNo());
    }

    private List<OpGoodsInfo> createOpGoodsInfoList(String ordNo, List<OrderProductView> orderProductViewList){
        return orderProductViewList.stream()
                .map(e -> e.toOpGoodsInfo(ordNo))
                .collect(Collectors.toList());
    }

    private List<OpClmInfo> createOpClmInfoList(OrderRequestVO orderRequestVO){
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

    private List<OpDvpAreaInfo> createOpDvpAreaInfoList(String ordNo, List<OrderDeliveryVO> orderDeliveryVOList){
        return orderDeliveryVOList.stream()
                .map(e -> e.toOpDvpAreaInfo(ordNo))
                .collect(Collectors.toList());
    }

    private List<OpDvpInfo> createOpDvpInfoList(OrderRequestVO orderRequestVO){
        List<OpDvpInfo> opDvpInfoList = new ArrayList<>();

        OrderBasicVO orderBasicVO = orderRequestVO.getOrderBasicVO();
        String dvMthdCd = OPT0001Code.of(orderBasicVO.getOrdTpCd()) == OPT0001Code.GENERAL ? DVP0001Code.DELIVERY.getCode() : DVP0001Code.NON_DELIVERY.getCode();

        List<OrderDeliveryVO> orderDeliveryVOList = orderRequestVO.getOrderDeliveryVOList();
        for (OrderDeliveryVO orderDeliveryVO : orderDeliveryVOList) {

            List<OrderDeliveryGroupInfoVO> orderDeliveryGroupInfoVOList = orderDeliveryVO.getOrderDeliveryGroupInfoVOList();
            for (OrderDeliveryGroupInfoVO orderDeliveryGroupInfoVO : orderDeliveryGroupInfoVOList) {
                OpDvpInfo opDvpInfo = orderDeliveryGroupInfoVO.toOpDvpInfo(orderRequestVO.getOrdNo(), orderDeliveryVO.getDvpSeq(), dvMthdCd);
                opDvpInfoList.add(opDvpInfo);
            }
        }

        return opDvpInfoList;
    }

    private List<OpOrdBnfInfo> createOpOrdBnfInfoList(OrderRequestVO orderRequestVO){
        List<OpOrdBnfInfo> opOrdBnfInfoList = new ArrayList<>();
        opOrdBnfInfoList.addAll(createPrdBnfInfoList(orderRequestVO.getOrdNo(), orderRequestVO.getOrderProductVOList()));
        opOrdBnfInfoList.addAll(createCartBnfInoList(orderRequestVO.getOrdNo(), orderRequestVO.getOrderBenefitVOList()));

        return opOrdBnfInfoList;
    }

    private List<OpOrdBnfInfo> createPrdBnfInfoList(String ordNo, List<OrderProductVO> orderProductVOList){
        return orderProductVOList.stream()
                .flatMap(orderProductVO -> orderProductVO.getProductBenefits().stream())
                .map(orderBenefitBaseVO -> orderBenefitBaseVO.toOpOrdBnfInfo(orderMapper.getBnfNo()))
                .collect(Collectors.toList());
    }

    private List<OpOrdBnfInfo> createCartBnfInoList(String ordNo, List<OrderBenefitVO> orderBenefitVOList){
        return orderBenefitVOList.stream()
                .map(orderBenefitVO -> orderBenefitVO.toOpOrdBnfInfo(orderMapper.getBnfNo()))
                .collect(Collectors.toList());
    }

    private List<OpOrdBnfRelInfo> createOpOrdBnfRelInfoList(OrderRequestVO orderRequestVO, List<OpClmInfo> opClmInfoList, List<OpOrdBnfInfo> opOrdBnfInfoList){
        Map<String, OpClmInfo> opClmInfoMap = toOpClmInfoMapByGoodsNoItemNo(opClmInfoList);
        Map<Long, OpOrdBnfInfo> opOrdBnfInfoMap = toOpOrdBnfInfoMapByCpnIssNo(opOrdBnfInfoList);

        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = new ArrayList<>();
        opOrdBnfRelInfoList.addAll(createPrdBnfRelInfoList(orderRequestVO.getOrderProductVOList(), opClmInfoMap, opOrdBnfInfoMap));
        opOrdBnfRelInfoList.addAll(createCartBnfRelInfoList(orderRequestVO, opClmInfoMap, opOrdBnfInfoMap));

        return opOrdBnfRelInfoList;
    }

    private List<OpOrdBnfRelInfo> createPrdBnfRelInfoList(List<OrderProductVO> orderProductVOList, Map<String, OpClmInfo> ordClmMap, Map<Long, OpOrdBnfInfo> ordBnfMap){
        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = new ArrayList<>();

        for (OrderProductVO orderProductVO : orderProductVOList) {
            String ordNo = getOrdNoFromOrdClmMap(orderProductVO.getGoodsNoItemNo(), ordClmMap);
            int ordSeq = getOrdSeqFromOrdClmMap(orderProductVO.getGoodsNoItemNo(), ordClmMap);

            List<OrderBenefitBaseVO> productBenefits = orderProductVO.getProductBenefits();
            for (OrderBenefitBaseVO productBenefit : productBenefits) {
                String ordBnfNo = getOrdBnfFromOrdBnfMap(productBenefit.getCpnIssNo(), ordBnfMap);

                OpOrdBnfRelInfo opOrdBnfRelInfo = productBenefit.toOpOrdBnfRelInfo(ordNo, ordSeq, ordBnfNo);
                opOrdBnfRelInfoList.add(opOrdBnfRelInfo);
            }
        }

        return opOrdBnfRelInfoList;
    }

    private List<OpOrdBnfRelInfo> createCartBnfRelInfoList(OrderRequestVO orderRequestVO, Map<String, OpClmInfo> ordClmMap, Map<Long, OpOrdBnfInfo> ordBnfMap){
        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = new ArrayList<>();

        Map<String, OrderProductVO> orderProductVOMap = orderRequestVO.setUpOrderProductVOMap();

        List<OrderBenefitVO> orderBenefitVOList = orderRequestVO.getOrderBenefitVOList();
        for (OrderBenefitVO orderBenefitVO : orderBenefitVOList) {
            String ordBnfNo = getOrdBnfFromOrdBnfMap(orderBenefitVO.getCpnIssNo(), ordBnfMap);
            //혜택 금액
            int aplyAmt = orderBenefitVO.getAplyAmt();

            List<OrderProductBaseVO> orderProductBaseVOList = orderBenefitVO.getOrderProductBaseVOList();
            //적용상품 총 금액(이전 차수 혜택 적용 금액)
            long totalPrdBnfAplyOrdPrc = getTotalPrdBnfAplyOrdPrc(orderProductBaseVOList, orderProductVOMap);

            for (OrderProductBaseVO orderProductBaseVO : orderProductBaseVOList) {
                String goodsNoItemNo = orderProductBaseVO.getGoodsNoItemNo();
                String ordNo = getOrdNoFromOrdClmMap(goodsNoItemNo, ordClmMap);
                int ordSeq = getOrdSeqFromOrdClmMap(goodsNoItemNo, ordClmMap);

                OrderProductVO orderProductVO = orderProductVOMap.get(goodsNoItemNo);
                long distribueAplyAmt = distribueAplyAmt(orderProductVO.getPrdBnfAplyOrdPrc(), totalPrdBnfAplyOrdPrc, aplyAmt);

                OpOrdBnfRelInfo opOrdBnfRelInfo = OpOrdBnfRelInfo.builder()
                        .ordBnfNo(ordBnfNo)
                        .ordBnfNo(ordNo)
                        .ordSeq(ordSeq)
                        .procSeq(1)
                        .aplyCnclCcd("")
                        .aplyAmt(distribueAplyAmt)
                        .build();

                opOrdBnfRelInfoList.add(opOrdBnfRelInfo);
            }
        }
        return opOrdBnfRelInfoList;
    }

    private Map<String, OpClmInfo> toOpClmInfoMapByGoodsNoItemNo(List<OpClmInfo> opClmInfoList){
        return opClmInfoList.stream()
                .collect(Collectors.toMap(OpClmInfo::getGoodsNoItemNo, Function.identity()));
    }

    private Map<Long, OpOrdBnfInfo> toOpOrdBnfInfoMapByCpnIssNo(List<OpOrdBnfInfo> opOrdBnfInfoList){
        return opOrdBnfInfoList.stream()
                .collect(Collectors.toMap(OpOrdBnfInfo::getCpnIssNo, Function.identity()));
    }

    private String getOrdNoFromOrdClmMap(String goodsNoItemNo, Map<String, OpClmInfo> ordClmMap){
        OpClmInfo opClmInfo = ordClmMap.get(goodsNoItemNo);
        return opClmInfo.getClmNo();
    }

    private int getOrdSeqFromOrdClmMap(String goodsNoItemNo, Map<String, OpClmInfo> ordClmMap){
        OpClmInfo opClmInfo = ordClmMap.get(goodsNoItemNo);
        return opClmInfo.getOrdSeq();
    }

    private String getOrdBnfFromOrdBnfMap(Long cpnIssNo, Map<Long, OpOrdBnfInfo> ordBnfInfoMap){
        OpOrdBnfInfo opOrdBnfInfo = ordBnfInfoMap.get(cpnIssNo);
        return opOrdBnfInfo.getOrdBnfNo();
    }

    private long getTotalPrdBnfAplyOrdPrc(List<OrderProductBaseVO> orderProductBaseVOList, Map<String, OrderProductVO> orderProductVOMap){
        return orderProductBaseVOList.stream()
                .map(e -> orderProductVOMap.get(e.getGoodsNoItemNo()))
                .mapToLong(OrderProductVO::getPrdBnfAplyOrdPrc)
                .sum();
    }

    private long distribueAplyAmt(long prdBnfAplyOrdPrc, long totalPrdBnfApyOrdPrc, long aplyAmt){
        return (prdBnfAplyOrdPrc / totalPrdBnfApyOrdPrc) * aplyAmt;
    }
}
