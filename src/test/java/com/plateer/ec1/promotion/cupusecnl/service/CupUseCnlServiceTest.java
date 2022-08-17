package com.plateer.ec1.promotion.cupusecnl.service;

import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupRestoreRequestVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupUseRequestVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CupUseCnlServiceTest {

    @Autowired
    private CupUseCnlService cupUseCnlService;

    @Test
    @DisplayName("쿠폰 사용 처리 시 주문 번호가 없을 경우 예외 발생")
    void null_ord_no(){
        CupIssVO cupIssVO = CupIssVO.builder().cpnIssNo(22L).mbrNo("user1").build();
        List<CupIssVO> cupIssVOS = Collections.singletonList(cupIssVO);

        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.useCup(cupIssVOS));
    }

    @Test
    @DisplayName("쿠폰 사용 처리 시 발급번호가 없을 경우 예외 발생")
    void null_use_cup_iss(){
        CupIssVO cupIssVO = CupIssVO.builder().ordNo("123").mbrNo("user1").build();
        List<CupIssVO> cupIssVOS = Collections.singletonList(cupIssVO);

        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.useCup(cupIssVOS));
    }

    @Test
    @DisplayName("쿠폰 사용 처리 시 회원번호가 없을 경우 예외 발생")
    void null_use_mbr_no(){
        CupIssVO cupIssVO = CupIssVO.builder().cpnIssNo(22L).ordNo("123").build();
        List<CupIssVO> cupIssVOS = Collections.singletonList(cupIssVO);

        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.useCup(cupIssVOS));
    }


    @Test
    @DisplayName("쿠폰 복원 시 쿠폰 발급번호가 없을 경우 예외 발생")
    void null_cup_iss(){
        CupIssVO cupIssVO = CupIssVO.builder().mbrNo("user1").build();
        List<CupIssVO> cupIssVOS = Collections.singletonList(cupIssVO);

        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.restoreCup(cupIssVOS));
    }

    @Test
    @DisplayName("쿠폰 복원 시 회원번호가 없을 경우 예외 발생")
    void null_mbr_no_cup_iss(){
        CupIssVO cupIssVO = CupIssVO.builder().cpnIssNo(24L).build();
        List<CupIssVO> cupIssVOS = Collections.singletonList(cupIssVO);

        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.restoreCup(cupIssVOS));
    }

    @Test
    @DisplayName("쿠폰 복원이 완료된다.")
    void cup_iss_pass(){
        CupIssVO cupIssVO = CupIssVO.builder().cpnIssNo(24L).mbrNo("123").build();
        List<CupIssVO> cupIssVOS = Collections.singletonList(cupIssVO);

        cupUseCnlService.restoreCup(cupIssVOS);
    }

}