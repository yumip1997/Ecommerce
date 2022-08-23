package com.plateer.ec1.claim.strategy.after.impl;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.payment.service.PayService;
import com.plateer.ec1.promotion.cupusecnl.service.CupUseCnlService;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompleteAfterStrategy implements ClaimAfterStrategy {

    private final PayService payService;
    private final CupUseCnlService cupUseCnlService;

    @Override
    public void call(ClaimRequestVO claimRequestVO){
        //TODO 복합결제일 경우 환불 결제수단 우선순위에 따라 환불처리
        payService.cancel(claimRequestVO.toPaymentCancelReqVO());
        restoreCup(claimRequestVO.toCupIssVOList());
    }

    public void restoreCup(List<CupIssVO> cupInfoVOList){
        if(CollectionUtils.isEmpty(cupInfoVOList)) return;

        cupUseCnlService.restoreCup(cupInfoVOList);
    }

    @Override
    public ClaimStatusType getType() {
        return ClaimStatusType.COMPLETE;
    }

}
