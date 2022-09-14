package com.plateer.ec1.claim.creator.insert.opcost.impl;

import com.plateer.ec1.claim.creator.insert.opcost.OpCostInsertCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimDeliveryCostInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.GEA;

@Component
public class ExchAcptCostCreator implements OpCostInsertCreator {

    @Override
    public List<OpOrdCostInfo> create(ClaimView claimView) {
        return claimView.getClaimDeliveryCostInfoList().stream()
                .map(ClaimDeliveryCostInfo::toOpOrdCostInfoExchange)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Collections.singletonList(GEA);
    }
}
