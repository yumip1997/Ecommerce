package com.plateer.ec1.claim.creator.insert.bnfrel.impl;

import com.plateer.ec1.claim.creator.insert.bnfrel.OpBnfRelInsertCreator;
import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

@Component
public class ApplyCancelBnfCreator implements OpBnfRelInsertCreator {

    @Override
    public List<OpOrdBnfRelInfo> create(ClaimView claimView) {
        return claimView.getOrderBenefitBaseVOList().stream()
                .map(OrderBenefitBaseVO::toOpOrdBnfRelInfoOfReverseAplyCcd)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCA, GRA, GEA, GRW);
    }
}
