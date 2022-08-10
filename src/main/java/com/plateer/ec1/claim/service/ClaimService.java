package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.factory.ClaimAfterStrategyFactory;
import com.plateer.ec1.claim.factory.ClaimValidatorFactory;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import com.plateer.ec1.claim.strategy.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimContextVO;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClaimService {

    private final ClaimValidatorFactory claimValidatorFactory;
    private final ClaimAfterStrategyFactory claimAfterStrategyFactory;
    private final ClaimContext claimContext;

    @LogTrace
    public void claim(ClaimRequestVO claimRequestVO){
        claimContext.doClaim(claimRequestVO, getClaimContextVO(claimRequestVO));
    }

    private ClaimContextVO getClaimContextVO(ClaimRequestVO claimRequestVO){
        ClaimDefine claimDefine = ClaimDefine.of(claimRequestVO);
        return ClaimContextVO.builder()
                .claimDefine(claimDefine)
                .claimValidator(claimValidatorFactory.get(claimDefine.getClaimStatusType()))
                .claimAfterStrategy(claimAfterStrategyFactory.get(claimDefine.getClaimStatusType()))
                .build();
    }
}
