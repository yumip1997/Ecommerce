package com.plateer.ec1.claim.strategy.after;

import com.plateer.ec1.claim.enums.ClaimStatusType;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.factory.CustomFactory;

public interface ClaimAfterStrategy extends CustomFactory<ClaimStatusType> {

    void call(ClaimRequestVO claimRequestVO);
}
