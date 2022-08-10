package com.plateer.ec1.claim.strategy.after.impl;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import com.plateer.ec1.payment.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompleteAfterStrategy implements ClaimAfterStrategy {

    private final PayService payService;

    @Override
    public ClaimStatusType getType() {
        return ClaimStatusType.COMPLETE;
    }

}
