package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.payment.enums.OPT0009Code;
import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.resource.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PointProcessorTest {

    @Autowired
    private PointProcessor pointProcessor;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "payment");
    }

    @Test
    void approve_test(){
        OrderInfoVO orderInfoVO = jsonReaderUtil.getObject("/OrderInfo.json", OrderInfoVO.class);
        PayInfoVO payInfoVO = jsonReaderUtil.getObject("/PayInfo.json", PayInfoVO.class);
        orderInfoVO.setPaymentBusiness(PaymentBusiness.POINT_APV);
        payInfoVO.setPaymentType(PaymentType.POINT);
        payInfoVO.setMethodType(OPT0009Code.POINT);

        ApproveResVO approveResVO = pointProcessor.approvePay(orderInfoVO, payInfoVO);
        Assertions.assertThat(approveResVO.getPaymentType()).isEqualTo(PaymentType.POINT);
    }

    @Test
    @DisplayName("취소요청 포인트 금액이 환불가능금액보다 클 때 예외 발생")
    void fail_point_cancel(){
        OrderPayInfoVO orderPayInfoVO = jsonReaderUtil.getObject("/CancelPointResDummy.json", OrderPayInfoVO.class);
        orderPayInfoVO.setPayPrgsScd("20");
        orderPayInfoVO.setCnclReqAmt(20000);
        assertThrows(ConstraintViolationException.class, () -> pointProcessor.cancelPay(orderPayInfoVO));
    }

    @Test
    @DisplayName("포인트 취소 테스트")
    void point_cancel(){
        OrderPayInfoVO orderPayInfoVO = jsonReaderUtil.getObject("/CancelPointResDummy.json", OrderPayInfoVO.class);
        orderPayInfoVO.setPayPrgsScd("20");
        orderPayInfoVO.setCnclReqAmt(7200);
        pointProcessor.cancelPay(orderPayInfoVO);
    }

}