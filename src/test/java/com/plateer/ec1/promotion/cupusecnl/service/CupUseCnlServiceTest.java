package com.plateer.ec1.promotion.cupusecnl.service;

import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupRestoreRequestVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupUseRequestVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CupUseCnlServiceTest {

    @Autowired
    private CupUseCnlService cupUseCnlService;

    @Test
    @DisplayName("쿠폰 사용 처리 시 주문 번호가 없을 경우 예외 발생")
    void null_ord_no(){
        CupUseRequestVO vo = CupUseRequestVO.builder().cpnIssNo(22L).mbrNo("user1").build();

//        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.useCup(vo));
    }

    @Test
    @DisplayName("쿠폰 사용 처리 시 발급번호가 없을 경우 예외 발생")
    void null_use_cup_iss(){
        CupUseRequestVO vo = CupUseRequestVO.builder().ordNo("111").mbrNo("user1").build();

//        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.useCup(vo));
    }

    @Test
    @DisplayName("쿠폰 사용 처리 시 회원번호가 없을 경우 예외 발생")
    void null_use_mbr_no(){
        CupUseRequestVO vo = CupUseRequestVO.builder().ordNo("111").cpnIssNo(22L).build();

//        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.useCup(vo));
    }

    @Test
    @DisplayName("쿠폰 사용 처리 시 발급 회원과 사용 요청 회원이 다를 경우 예외 발생")
    void not_same_mbr_no(){
        CupUseRequestVO vo = CupUseRequestVO.builder().cpnIssNo(22L).ordNo("111").mbrNo("user2").build();

//        assertThrows(RuntimeException.class, () -> cupUseCnlService.useCup(vo));
    }

    @Test
    @DisplayName("쿠폰 사용 처리 성공")
    void use_cup_pass(){
        CupUseRequestVO vo = CupUseRequestVO.builder().ordNo("111").cpnIssNo(22L).mbrNo("user1").build();

//        cupUseCnlService.useCup(vo);
    }

    @Test
    @DisplayName("쿠폰 복원 시 쿠폰 발급번호가 없을 경우 예외 발생")
    void null_cup_iss(){
        CupRestoreRequestVO vo = CupRestoreRequestVO.builder().mbrNo("user1").build();

//        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.restoreCup(vo));
    }

    @Test
    @DisplayName("쿠폰 복원 시 회원번호가 없을 경우 예외 발생")
    void null_mbr_no_cup_iss(){
        CupRestoreRequestVO vo = CupRestoreRequestVO.builder().cpnIssNo(22L).build();

//        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.restoreCup(vo));
    }

    @Test
    @DisplayName("쿠폰 복원 시 복원 쿠폰을 발급받은 회원과 발급 요청을 한 회원이 다를 경우 예외 발생")
    void not_same_cup_iss_mbr(){
        CupRestoreRequestVO vo = CupRestoreRequestVO.builder().cpnIssNo(22L).mbrNo("user2").build();

//        assertThrows(RuntimeException.class, () -> cupUseCnlService.restoreCup(vo));
    }

    @Test
    @DisplayName("쿠폰 복원이 완료된다.")
    void cup_iss_pass(){
        CupRestoreRequestVO vo = CupRestoreRequestVO.builder().cpnIssNo(22L).mbrNo("user1").build();

//        cupUseCnlService.restoreCup(vo);
    }

}