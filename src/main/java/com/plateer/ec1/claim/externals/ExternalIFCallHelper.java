package com.plateer.ec1.claim.externals;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.factory.MultiValueCustomFactory;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import org.springframework.util.CollectionUtils;

import java.util.List;

public interface ExternalIFCallHelper extends MultiValueCustomFactory<ClaimBusiness> {

    void call(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO);

    static void callAll(ClaimRequestVO claimRequestVO, OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO, List<ExternalIFCallHelper> callers){
        if(CollectionUtils.isEmpty(callers)) return;

        for (ExternalIFCallHelper caller : callers) {
            caller.call(claimRequestVO, creationVO);
        }
    }
}
