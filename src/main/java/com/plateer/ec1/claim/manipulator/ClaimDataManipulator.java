package com.plateer.ec1.claim.manipulator;

import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ClaimDataManipulator {

    @Transactional
    public void manipulateClaimData(ClaimInsertBase claimInsertBase, ClaimUpdateBase claimUpdateBase){
        insertClaimData(claimInsertBase);
        updateClaimData(claimUpdateBase);
    }

    public void insertClaimData(ClaimInsertBase claimInsertBase){
    }

    public void updateClaimData(ClaimUpdateBase claimUpdateBase){
    }

}
