package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.strategy.after.ClaimAfterStrategy;
import com.plateer.ec1.claim.strategy.validator.ClaimValidator;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClaimContextVO {

    private ClaimDefine claimDefine;
    private ClaimValidator claimValidator;
    private ClaimAfterStrategy claimAfterStrategy;

}
