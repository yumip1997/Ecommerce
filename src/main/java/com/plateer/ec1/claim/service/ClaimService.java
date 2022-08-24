package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.factory.ClaimAfterStrategyFactory;
import com.plateer.ec1.claim.factory.ClaimValidatorFactory;
import com.plateer.ec1.claim.validation.groups.Claim;
import com.plateer.ec1.claim.vo.ClaimContextVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Validated
public class ClaimService {

    private final ClaimValidatorFactory claimValidatorFactory;
    private final ClaimAfterStrategyFactory claimAfterStrategyFactory;
    private final ClaimContext claimContext;

    //TODO 필수 값 체크 VALIDATION 수정 예정
    @LogTrace
    @Validated(Claim.class)
    public void claim(@Valid ClaimRequestVO claimRequestVO){
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
