package com.plateer.ec1.claim.validation.verifier;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.factory.StrategyTypes;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import org.springframework.util.CollectionUtils;

import java.util.List;

public interface AmountVerifier extends StrategyTypes<ClaimBusiness> {

    void verifyAmount(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO);

    static void verifyAmountAll(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO, List<AmountVerifier> verifiers){
        if(CollectionUtils.isEmpty(verifiers)) return;

        for (AmountVerifier verifier : verifiers) {
            verifier.verifyAmount(claimRequestVO, creationVO);
        }
    }
}
