package com.plateer.ec1.claim.validation.verifier;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import com.plateer.ec1.order.vo.OrdClmCreationVO;

public interface AmountVerifier extends MultiValueCustomFactory<ClaimBusiness> {

    void verifyAmount(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO);

}
