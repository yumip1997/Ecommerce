package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.factory.ClaimValidatorFactory;
import com.plateer.ec1.claim.factory.ExternalIFCallHelperFactory;
import com.plateer.ec1.claim.vo.ClaimContextVO;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
@Validated
public class ClaimService {

    private final ExternalIFCallHelperFactory externalIFCallHelperFactory;
    private final ClaimValidatorFactory claimValidatorFactory;
    private final ClaimContext claimContext;

    @LogTrace
    @Validated
    public void claim(@Valid ClaimRequestVO claimRequestVO){
        claimContext.doClaim(claimRequestVO, getClaimContextVO(claimRequestVO));
    }

    private ClaimContextVO getClaimContextVO(ClaimRequestVO claimRequestVO){
        ClaimBusiness claimBusiness = ClaimBusiness.of(claimRequestVO);
        return ClaimContextVO.builder()
                .claimBusiness(claimBusiness)
                .validatorList(claimValidatorFactory.getValues(claimBusiness))
                .ifCallHelperList(externalIFCallHelperFactory.getValues(claimBusiness))
                .build();
    }
}
