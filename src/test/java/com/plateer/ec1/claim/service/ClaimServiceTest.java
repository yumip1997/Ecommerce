package com.plateer.ec1.claim.service;

import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    public void gnr_all_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestAllCnclGeneral.json", ClaimRequestVO.class);
        claimService.claim(object);
    }

    @Test
    public void gnr_partial_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestPartialCnlGeneral.json", ClaimRequestVO.class);
        claimService.claim(object);
    }

    @Test
    public void return_company_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/RA_company.json", ClaimRequestVO.class);
        claimService.claim(object);
    }

    @Test
    public void return_customer_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/RA_customer.json", ClaimRequestVO.class);
        claimService.claim(object);
    }

}