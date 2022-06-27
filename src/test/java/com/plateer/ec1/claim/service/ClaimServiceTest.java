package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.vo.ClaimBaseVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClaimServiceTest {

    @Autowired
    ClaimService claimService;

    @Test
    @DisplayName("일반주문취소완료 프로세스 확인")
    void test_gcc(){
        ClaimBaseVO claimBaseVO = ClaimBaseVO.builder().claimType("GCC").goodsSellTpCd("10").build();
        claimService.claim(claimBaseVO);
    }

    @Test
    @DisplayName("반품접수 프로세스 확인")
    void test_ra(){
        ClaimBaseVO claimBaseVO = ClaimBaseVO.builder().claimType("RA").goodsSellTpCd("10").build();
        claimService.claim(claimBaseVO);
    }

    @Test
    @DisplayName("반품철회 프로세스 확인")
    void test_rw(){
        ClaimBaseVO claimBaseVO = ClaimBaseVO.builder().claimType("RW").goodsSellTpCd("10").build();
        claimService.claim(claimBaseVO);
    }
}