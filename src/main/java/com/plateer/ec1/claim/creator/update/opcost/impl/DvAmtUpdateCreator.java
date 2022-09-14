package com.plateer.ec1.claim.creator.update.opcost.impl;

import com.plateer.ec1.claim.creator.update.opcost.OpCostUpdateCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimDeliveryCostInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.GEW;
import static com.plateer.ec1.claim.enums.ClaimBusiness.GRW;

@Component
public class DvAmtUpdateCreator implements OpCostUpdateCreator {

    @Override
    public List<OpOrdCostInfo> create(ClaimView claimView) {
        return claimView.getClaimDeliveryCostInfoList().stream()
                .filter(ClaimDeliveryCostInfo::isApply)
                .map(ClaimDeliveryCostInfo::toUpdateCnclDvAmtOpOrdCostInfo)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GRW, GEW);
    }
}
