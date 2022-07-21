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

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class InicisProcessorTest {

    @Autowired
    private InicisProcessor inicisProcessor;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "payment");
    }

    @Test
    void approve_test(){
        OrderInfoVO orderInfoVO = jsonReaderUtil.getObject("/OrderInfo.json", OrderInfoVO.class);
        PayInfoVO payInfoVO = jsonReaderUtil.getObject("/PayInfo.json", PayInfoVO.class);
        orderInfoVO.setPaymentBusiness(PaymentBusiness.VACCT_APV);
        payInfoVO.setMethodType(OPT0009Code.VIRTUAL_ACCOUNT);

        ApproveResVO approveResVO = inicisProcessor.approvePay(orderInfoVO, payInfoVO);
        Assertions.assertThat(approveResVO.getPaymentType()).isEqualTo(PaymentType.INICIS);
    }

    @Test
    @DisplayName("입금 전 전체 취소 테스트")
    void test_all_cancel_before(){
        OrderPayInfoVO orderPayInfoVO = jsonReaderUtil.getObject("/CancelResDummy.json", OrderPayInfoVO.class);
        orderPayInfoVO.setPayPrgsScd("10");
        orderPayInfoVO.setCnclReqAmt(16200);
        inicisProcessor.cancelPay(orderPayInfoVO);
    }

    @Test
    @DisplayName("입금 전 부분 취소 테스트")
    void test_partial_cancel_before(){
        OrderPayInfoVO orderPayInfoVO = jsonReaderUtil.getObject("/CancelResDummy.json", OrderPayInfoVO.class);
        orderPayInfoVO.setPayPrgsScd("10");
        orderPayInfoVO.setCnclReqAmt(7200);
        inicisProcessor.cancelPay(orderPayInfoVO);
    }

     @Test
     @DisplayName("입금 전 취소요청금액이 결제금액보다 클 때 예외 발생")
     void fail_cancel_before_amt(){
         OrderPayInfoVO orderPayInfoVO = jsonReaderUtil.getObject("/CancelResDummy.json", OrderPayInfoVO.class);
         orderPayInfoVO.setPayPrgsScd("10");
         orderPayInfoVO.setCnclReqAmt(20000);
         assertThrows(ConstraintViolationException.class, () -> inicisProcessor.cancelPay(orderPayInfoVO));
     }

    @Test
    @DisplayName("입금 후 취소요청금액이 환불가능금액보다 클 때 예외 발생")
    void fail_cancel_after_amt(){
        OrderPayInfoVO orderPayInfoVO = jsonReaderUtil.getObject("/CancelInfoVO.json", OrderPayInfoVO.class);
        orderPayInfoVO.setPayPrgsScd("20");
        orderPayInfoVO.setCnclReqAmt(20000);
        assertThrows(ConstraintViolationException.class, () -> inicisProcessor.cancelPay(orderPayInfoVO));
    }

}