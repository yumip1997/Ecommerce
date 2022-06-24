package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.vo.ClaimVO;
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
        ClaimVO claimVO = ClaimVO.builder().claimType("GCC").productType("G").build();
        claimService.claim(claimVO);
    }

    @Test
    @DisplayName("반품접수 프로세스 확인")
    void test_ra(){
        ClaimVO claimVO = ClaimVO.builder().claimType("RA").productType("G").build();
        claimService.claim(claimVO);
    }

    @Test
    @DisplayName("반품철회 프로세스 확인")
    void test_rw(){
        ClaimVO claimVO = ClaimVO.builder().claimType("RW").productType("G").build();
        claimService.claim(claimVO);
    }
}