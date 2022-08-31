package com.plateer.ec1.claim.externals;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;

public interface ExternalIFCallHelper extends MultiValueCustomFactory<ClaimBusiness> {

    void call(ClaimView claimView);

}
