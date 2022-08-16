package com.plateer.ec1.claim.strategy.validator.impl;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.excpetion.custom.ValidationException;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CompleteValidatorTest {

    @Autowired
    private CompleteValidator completeValidator;
    @Autowired
    private ClaimMapper claimMapper;
    private JsonReaderUtil jsonReaderUtil;
    private ClaimRequestVO claimRequestAllCnclGeneral;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "claim");
        claimRequestAllCnclGeneral = jsonReaderUtil.getObject("/ClaimRequestAllCnclGeneral.json", ClaimRequestVO.class);
    }

    @Test
    @DisplayName("일반상품주문취소 요청 시, 클레임 상품의 주문상태가 배송완료된 상태일 경우 예외가 발생한다.")
    void cancel_general_ordPrgs(){
        List<ClaimView> claimViewList = claimMapper.getClaimViewList(claimRequestAllCnclGeneral.getClaimGoodsInfoList());
        ClaimDefine claimDefine = ClaimDefine.of(claimRequestAllCnclGeneral);
        ClaimView claimView = claimViewList.get(0);
        claimView.setOrdPrgsScd("40");
        Assertions.assertThrows(ValidationException.class, () -> completeValidator.isValidOrdPrgsScd(claimViewList, claimDefine.getValidOrdPrgsList()));
    }

    @Test
    @DisplayName("일반상품주문취소 시, 클레임 상품에 모바일 쿠폰이 포함되어있을 경우 예외가 발생한다.")
    void cancel_general_prdType(){
        List<ClaimView> claimViewList = claimMapper.getClaimViewList(claimRequestAllCnclGeneral.getClaimGoodsInfoList());
        ClaimDefine claimDefine = ClaimDefine.of(claimRequestAllCnclGeneral);
        ClaimView claimView = claimViewList.get(0);
        claimView.setGoodsSellTpCd("20");
        Assertions.assertThrows(ValidationException.class, () -> completeValidator.isValidProductType(claimViewList, claimDefine.getPrdType()));
    }

}