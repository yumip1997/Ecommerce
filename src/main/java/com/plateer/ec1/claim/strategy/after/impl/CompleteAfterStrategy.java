package com.plateer.ec1.claim.strategy.after.impl;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.promotion.cupusecnl.service.CupUseCnlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteAfterStrategy implements ClaimAfterStrategy {

    private final PayService payService;
    private final CupUseCnlService cupUseCnlService;

    //TODO 수정하기
    @Override
    public void call(ClaimRequestVO claimRequestVO){
        payService.cancel(null);
        cupUseCnlService.restoreCup(claimRequestVO.toCupIssVOList());
    }

    @Override
    public ClaimStatusType getType() {
        return ClaimStatusType.COMPLETE;
    }

}
