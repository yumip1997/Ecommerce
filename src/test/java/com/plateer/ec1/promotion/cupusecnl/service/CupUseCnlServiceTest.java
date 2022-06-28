package com.plateer.ec1.promotion.cupusecnl.service;

import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupRestoreRequestVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupUseRequestVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CupUseCnlServiceTest {

    @Autowired
    private CupUseCnlService cupUseCnlService;

    @Test
    @DisplayName("쿠폰 사용 처리 시 주문 번호가 없을 경우 예외 발생")
    void null_ord_no(){
        CupUseRequestVO vo = CupUseRequestVO.builder().ordNo("111").build();

        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.useCup(vo));
    }

    @Test
    @DisplayName("쿠폰 사용 처리 시 발급번호가 없을 경우 예외 발생")
    void null_use_cup_iss(){
        CupUseRequestVO vo = CupUseRequestVO.builder().cpnIssNo(2L).build();

        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.useCup(vo));
    }

    @Test
    @DisplayName("쿠폰 사용 처리 성공")
    void use_cup_pass(){
        CupUseRequestVO vo = CupUseRequestVO.builder().ordNo("111").cpnIssNo(2L).build();

        cupUseCnlService.useCup(vo);
    }

    @Test
    @DisplayName("쿠폰 복원 시 쿠폰 발급번호가 없을 경우 예외 발생")
    void null_cup_iss(){
        CupRestoreRequestVO vo = CupRestoreRequestVO.builder().build();

        assertThrows(ConstraintViolationException.class, () -> cupUseCnlService.restoreCup(vo));
    }

    @Test
    @DisplayName("쿠폰 복원이 완료된다.")
    void cup_iss_pass(){
        CupRestoreRequestVO vo = CupRestoreRequestVO.builder().cpnIssNo(2L).build();

        cupUseCnlService.restoreCup(vo);
    }

}