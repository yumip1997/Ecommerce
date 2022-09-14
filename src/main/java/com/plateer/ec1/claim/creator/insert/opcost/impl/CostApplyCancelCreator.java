package com.plateer.ec1.claim.creator.insert.opcost.impl;

import com.plateer.ec1.claim.creator.insert.opcost.OpCostInsertCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimDeliveryCostInfo;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;
import static java.util.stream.Collectors.toList;

@Component
public class CostApplyCancelCreator implements OpCostInsertCreator {

    @Override
    public List<OpOrdCostInfo> create(ClaimView claimView) {
        return claimView.getClaimDeliveryCostInfoList().stream()
                .map(ClaimDeliveryCostInfo::toOpOrdCostInfoOfCancel)
                .collect(toList());
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCA, GRW);
    }

}
