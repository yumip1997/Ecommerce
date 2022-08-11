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

    @Transactional(propagation = Propagation.REQUIRED)
    public void insertClaimData(ClaimInsertBase claimInsertBase){
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateClaimData(ClaimUpdateBase claimUpdateBase){
    }
}
