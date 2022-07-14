package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.excpetion.custom.BusinessException;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.res.VacctDpstCmtResVO;
import com.plateer.ec1.resource.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

        ApproveResVO approveResVO = inicisProcessor.approvePay(orderInfoVO, payInfoVO);
        Assertions.assertThat(approveResVO.getPaymentType()).isEqualTo(PaymentType.INICIS);
    }

    @Test
    @DisplayName("입금 통보 응답 코드가 성공이지 않을 때 예외 발생")
    void fail_vacct_deposit(){
        VacctDpstCmtResVO resVO = jsonReaderUtil.getObject("/VacctDpstCmtFailCode.json", VacctDpstCmtResVO.class);
        assertThrows(BusinessException.class, () -> inicisProcessor.completeVacctDeposit(resVO));
     }
}