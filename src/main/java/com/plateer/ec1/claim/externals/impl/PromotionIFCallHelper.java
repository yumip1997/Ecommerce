package com.plateer.ec1.claim.externals.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.externals.ExternalIFCallHelper;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.promotion.cupusecnl.service.CupUseCnlService;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

@Component
@RequiredArgsConstructor
@Order(2)
public class PromotionIFCallHelper implements ExternalIFCallHelper {

    private final CupUseCnlService cupUseCnlService;

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCC);
    }

    @Override
    public void call(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO) {
        ClaimUpdateBase updateData = creationVO.getUpdateData();
        List<CupIssVO> cupIssVOList = claimRequestVO.toCupIssVOList(updateData.getRestoreCpnIssNoList());

        if(CollectionUtils.isEmpty(cupIssVOList)) return;
        cupUseCnlService.restoreCup(cupIssVOList);
    }
}
