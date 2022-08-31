package com.plateer.ec1.claim.manipulator;

import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.mapper.OrdTrxMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClaimDataManipulator {

    private final OrdTrxMapper ordTrxMapper;

    @Transactional
    public void manipulateClaimData(ClaimInsertBase claimInsertBase, ClaimUpdateBase claimUpdateBase){
        insertClaimData(claimInsertBase);
        updateClaimData(claimUpdateBase);
    }

    private void insertClaimData(ClaimInsertBase claimInsertBase){
        insertOpClmInfoList(claimInsertBase.getOpClmInfoList());
        insertOpOrdCostInfoList(claimInsertBase.getOpOrdCostInfoList());
        insertOpOrdBnfRelInfoList(claimInsertBase.getOpOrdBnfRelInfoList());
    }

    private void insertOpClmInfoList(List<OpClmInfo> opClmInfoList) {
        if(CollectionUtils.isEmpty(opClmInfoList)) return;

        ordTrxMapper.insertOpClmInfoList(opClmInfoList);
    }

    private void insertOpOrdCostInfoList(List<OpOrdCostInfo> opOrdCostInfoList) {
        if(CollectionUtils.isEmpty(opOrdCostInfoList)) return;

        ordTrxMapper.insertOpOrdCostInfoList(opOrdCostInfoList);
    }

    private void insertOpOrdBnfRelInfoList(List<OpOrdBnfRelInfo> opOrdBnfRelInfoList){
        if(CollectionUtils.isEmpty(opOrdBnfRelInfoList)) return;

        ordTrxMapper.insertOpOrdBnfRelInfoList(opOrdBnfRelInfoList);
    }

    private void updateClaimData(ClaimUpdateBase claimUpdateBase){
        updateOpClmInfoList(claimUpdateBase.getOpClmInfoList());
        updateOpOrdCostInfoList(claimUpdateBase.getOpOrdCostInfoList());
        updateOpOrdBnfInfoList(claimUpdateBase.getOpOrdBnfInfoList());
    }

    private void updateOpClmInfoList(List<OpClmInfo> opClmInfoList) {
        if(CollectionUtils.isEmpty(opClmInfoList)) return;

        ordTrxMapper.updateOpClmInfoList(opClmInfoList);
    }

    private void updateOpOrdCostInfoList(List<OpOrdCostInfo> opOrdCostInfoList) {
        if(CollectionUtils.isEmpty(opOrdCostInfoList)) return;

        ordTrxMapper.updateOpOrdCostInfoList(opOrdCostInfoList);
    }

    private void updateOpOrdBnfInfoList(List<OpOrdBnfInfo> opOrdBnfInfoList) {
        if(CollectionUtils.isEmpty(opOrdBnfInfoList)) return;

        ordTrxMapper.updateOpOrdBnfInfoList(opOrdBnfInfoList);
    }

}
