package com.plateer.ec1.claim.vo;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class ClaimRequestVOTest {

    @Test
    void clone_test(){

        ClaimRequestVO claimRequestVO = new ClaimRequestVO();
        claimRequestVO.setClaimGoodsInfoList(new ArrayList<>());

        ClaimRequestVO clone = claimRequestVO.clone();
    }

}