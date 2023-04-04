package com.plateer.ec1.promotion.point.service;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.promotion.point.vo.PointVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointServiceTest {

    @Autowired
    private PointService pointService;

    @Test
    void save_test(){
        PointVO pointVO = PointVO.builder().mbrNo("user01").svUseAmt(100000000L).svUseCcd("10").build();
        pointService.savePoint(pointVO);
    }

    @Test
    void save_null_mbrNo(){
        PointVO pointVO = PointVO.builder().svUseAmt(100L).svUseCcd("10").build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> pointService.savePoint(pointVO));
    }

    @Test
    void save_null_svUseCcd(){
        PointVO pointVO = PointVO.builder().mbrNo("user1").svUseAmt(100L).build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> pointService.savePoint(pointVO));
    }

    @Test
    void save_null_svUseAmt(){
        PointVO pointVO = PointVO.builder().mbrNo("user1").svUseCcd("10").build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> pointService.savePoint(pointVO));
    }

    @Test
    void use_null_ordNo(){
        PointVO pointVO = PointVO.builder().mbrNo("user1").svUseCcd("20").build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> pointService.usePoint(pointVO));
    }

    @Test
    void use_null_svUseAmt(){
        PointVO pointVO = PointVO.builder().mbrNo("user2").svUseAmt(1000L).svUseCcd("20").ordNo("123").payNo("123").build();
        Assertions.assertThrows(BusinessException.class, () -> pointService.usePoint(pointVO));
    }

}