package com.plateer.ec1.claim.externals;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import com.plateer.ec1.promotion.cupusecnl.service.CupUseCnlService;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

import static com.plateer.ec1.claim.enums.ClaimBusiness.*;

@Component
@RequiredArgsConstructor
public class PromotionIFCallHelper implements MultiValueCustomFactory<ClaimBusiness> {

    private final CupUseCnlService cupUseCnlService;

    public void call(List<CupIssVO> cupIssVOList) {
        if(CollectionUtils.isEmpty(cupIssVOList)) return;
        cupUseCnlService.restoreCup(cupIssVOList);
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(GCC, MCC);
    }
}
