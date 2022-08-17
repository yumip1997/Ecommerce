package com.plateer.ec1.claim.vo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ClaimRequestVOTest {

    @Test
    void clone_test(){

        ClaimRequestVO claimRequestVO = new ClaimRequestVO();
        claimRequestVO.setClaimGoodsInfoList(new ArrayList<>());

        ClaimRequestVO clone = claimRequestVO.clone();
        System.out.println(clone.getClaimGoodsInfoList());
        System.out.println(claimRequestVO.getClaimGoodsInfoList());
    }

}