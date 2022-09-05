package com.plateer.ec1.claim.externals;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import com.plateer.ec1.order.vo.OrdClmCreationVO;

public interface ExternalIFCallHelper extends MultiValueCustomFactory<ClaimBusiness> {

    void call(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO);

}
