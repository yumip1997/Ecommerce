package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.vo.ClaimDeliveryCostInfo;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClaimServiceTest {


    @Autowired
    private ClaimService claimService;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "claim");
    }

    @Test
    @DisplayName("반품 접수 시 귀책구분 코드가 없을 경우 예외가 발생한다")
    public void empty_resons_return_accept(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/RA_customer.json", ClaimRequestVO.class);
        List<ClaimDeliveryCostInfo> claimDeliveryCostInfoList = object.getClaimDeliveryCostInfoList();
        ClaimDeliveryCostInfo claimDeliveryCostInfo = claimDeliveryCostInfoList.get(0);
        claimDeliveryCostInfo.setImtnRsnCcd(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> claimService.claim(object));
    }

    @Test
    @DisplayName("교환 접수 시 귀책구분 코드가 없을 경우 예외가 발생한다")
    public void empty_resons_exchange_accept(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/EA_customer.json", ClaimRequestVO.class);
        List<ClaimDeliveryCostInfo> claimDeliveryCostInfoList = object.getClaimDeliveryCostInfoList();
        ClaimDeliveryCostInfo claimDeliveryCostInfo = claimDeliveryCostInfoList.get(0);
        claimDeliveryCostInfo.setImtnRsnCcd(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> claimService.claim(object));
    }

    @Test
    @DisplayName("클레임 대상 주문 건을 찾을 수 없을 경우예외가 발생한다")
    public void not_find_clm(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestAllCnclGeneral.json", ClaimRequestVO.class);
        object.setOrdNo("100");
        Assertions.assertThrows(ValidationException.class, () -> claimService.claim(object));
    }

    @Test
    public void gnr_all_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestAllCnclGeneral.json", ClaimRequestVO.class);
        Assertions.assertDoesNotThrow(() -> claimService.claim(object));
    }

    @Test
    public void gnr_partial_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestPartialCnlGeneral.json", ClaimRequestVO.class);
        Assertions.assertDoesNotThrow(() -> claimService.claim(object));
    }

    @Test
    public void return_company_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/RA_company.json", ClaimRequestVO.class);
        Assertions.assertDoesNotThrow(() -> claimService.claim(object));
    }

    @Test
    public void return_customer_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/RA_customer.json", ClaimRequestVO.class);
        Assertions.assertDoesNotThrow(() -> claimService.claim(object));
    }

    @Test
    public void return_withdrawal_company_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/RW_company.json", ClaimRequestVO.class);
        Assertions.assertDoesNotThrow(() -> claimService.claim(object));
    }

    @Test
    public void return_withdrawal_customer_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/RW_customer.json", ClaimRequestVO.class);
        Assertions.assertDoesNotThrow(() -> claimService.claim(object));
    }

}