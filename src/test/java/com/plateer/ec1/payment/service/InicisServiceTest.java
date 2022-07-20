package com.plateer.ec1.payment.service;

import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.payment.vo.inicis.res.VacctDpstCmtResVO;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InicisServiceTest {

    @Autowired
    private InicisService inicisService;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "payment");
    }

    @Test
    @DisplayName("입금 통보 응답 코드가 성공이지 않을 때 예외 발생")
    void fail_vacct_deposit(){
        VacctDpstCmtResVO resVO = jsonReaderUtil.getObject("/VacctDpstCmtFailCode.json", VacctDpstCmtResVO.class);
        assertThrows(ConstraintViolationException.class, () -> inicisService.completeVacctDeposit(resVO));
    }

}