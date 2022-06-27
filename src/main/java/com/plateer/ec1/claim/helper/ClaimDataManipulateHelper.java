package com.plateer.ec1.claim.helper;

import com.plateer.ec1.claim.mapper.ClaimDao;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClaimDataManipulateHelper {

    private final ClaimDao claimDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertClaimData(ClaimInsertBase claimInsertBase){
        insertOrderClaim(claimInsertBase.getOpClmInfoList());
        insertOrderCost(claimInsertBase.getOpOrdCostInfoList());
        insertOrderBenefitRelation(claimInsertBase.getOpOrdBnfRelInfo());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateClaimData(ClaimUpdateBase claimUpdateBase){
        updateOrderClaim(claimUpdateBase.getOpClmInfo());
        updateOrgOrderCnt(claimUpdateBase.getOpClmInfo());
        updateOrderBenefit(claimUpdateBase.getOrderBenefit());
    }

    private void insertOrderClaim(List<OpClmInfo> opClmInfoList){
        if(CollectionUtils.isEmpty(opClmInfoList)) return;

        claimDao.insertOrderClaim(opClmInfoList);
    }

    private void insertOrderCost(List<OpOrdCostInfo> opOrdCostInfoList){
        if(CollectionUtils.isEmpty(opOrdCostInfoList)) return;

        claimDao.insertOrderCost(opOrdCostInfoList);
    }

    private void insertOrderBenefitRelation(OpOrdBnfRelInfo opOrdBnfRelInfo){
        if(ObjectUtils.isEmpty(opOrdBnfRelInfo)) return;

        claimDao.insertOrderBenefitRelation(opOrdBnfRelInfo);
    }

    private void updateOrderClaim(OpClmInfo opClmInfo){
        if(ObjectUtils.isEmpty(opClmInfo)) return;

        claimDao.updateOrderClaim(opClmInfo);
    }

    private void updateOrgOrderCnt(OpClmInfo opClmInfo){
        if(ObjectUtils.isEmpty(opClmInfo)) return;

        claimDao.updateOrgOrderCnt(opClmInfo);
    }

    private void updateOrderBenefit(OpOrdBnfInfo orderBenefit){
        if(ObjectUtils.isEmpty(orderBenefit)) return;

        claimDao.updateOrderBenefit(orderBenefit);
    }
}
