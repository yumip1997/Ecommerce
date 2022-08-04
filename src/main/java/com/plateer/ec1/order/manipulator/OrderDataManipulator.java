package com.plateer.ec1.order.manipulator;

import com.plateer.ec1.common.model.order.*;
import com.plateer.ec1.order.mapper.OrdTrxMapper;
import com.plateer.ec1.order.vo.OrderBasicVO;
import com.plateer.ec1.order.vo.OrderVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderDataManipulator {

    private final OrdTrxMapper ordTrxMapper;

    @Transactional
    public void insertOrder(OrderVO orderVO) {
        insertOrderBasic(orderVO.getOpOrdBase());
        insertOpGoodsInfo(orderVO.getOpGoodsInfoList());
        insertOpClmInfoList(orderVO.getOpClmInfoList());
        insertOpDvpAreaInfoList(orderVO.getOpDvpAreaInfoList());
        insertOpDvpInfoList(orderVO.getOpDvpInfoList());
        insertOpOrdCostInfoList(orderVO.getOpOrdCostInfoList());
        insertOpOrdBnfInfoList(orderVO.getOpOrdBnfInfoList());
        insertOpOrdBnfRelInfoList(orderVO.getOpOrdBnfRelInfoList());
    }

    private void insertOrderBasic(OpOrdBase opOrdBase) {
        ordTrxMapper.insertOpOrdBase(opOrdBase);
    }
    
    private void insertOpGoodsInfo(List<OpGoodsInfo> opGoodsInfoList){
        ordTrxMapper.insertOpGoodsInfo(opGoodsInfoList);
    }

    private void insertOpClmInfoList(List<OpClmInfo> opClmInfoList){
        ordTrxMapper.insertOpClmInfoList(opClmInfoList);
    }

    private void insertOpDvpAreaInfoList(List<OpDvpAreaInfo> opDvpAreaInfoList){
        ordTrxMapper.insertOpDvpAreaInfoList(opDvpAreaInfoList);
    }

    private void insertOpDvpInfoList(List<OpDvpInfo> opDvpInfoList){
        ordTrxMapper.insertOpDvpInfoList(opDvpInfoList);
    }

    private void insertOpOrdCostInfoList(List<OpOrdCostInfo> OpOrdCostInfoList){
        ordTrxMapper.insertOpOrdCostInfoList(OpOrdCostInfoList);
    }

    private void insertOpOrdBnfInfoList(List<OpOrdBnfInfo> opOrdBnfInfoList){
        if(CollectionUtils.isEmpty(opOrdBnfInfoList)) return;
        
        ordTrxMapper.insertOpOrdBnfInfoList(opOrdBnfInfoList);
    }

    private void insertOpOrdBnfRelInfoList(List<OpOrdBnfRelInfo> opOrdBnfRelInfoList){
        if(CollectionUtils.isEmpty(opOrdBnfRelInfoList)) return;
        
        ordTrxMapper.insertOpOrdBnfRelInfoList(opOrdBnfRelInfoList);
    }
}
