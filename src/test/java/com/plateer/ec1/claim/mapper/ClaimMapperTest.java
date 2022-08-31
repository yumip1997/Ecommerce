package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.resource.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClaimMapperTest {

    @Autowired
    private ClaimMapper claimMapper;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "claim");
    }

    @Test
    public void gnr_all_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestAllCnclGeneral.json", ClaimRequestVO.class);
        ClaimView claimView = claimMapper.getClaimView(object);

        Assertions.assertThat(claimView.getClaimDeliveryInfoList().size()).isEqualTo(object.getClaimDeliveryInfoList().size());
        Assertions.assertThat(claimView.getClaimGoodsInfoList().size()).isEqualTo(object.getClaimGoodsInfoList().size());
    }

    @Test
    public void gnr_partial_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestPartialCnlGeneral.json", ClaimRequestVO.class);
        ClaimView claimView = claimMapper.getClaimView(object);

        Assertions.assertThat(claimView.getClaimDeliveryInfoList().size()).isEqualTo(object.getClaimDeliveryInfoList().size());
        Assertions.assertThat(claimView.getClaimGoodsInfoList().size()).isEqualTo(object.getClaimGoodsInfoList().size());
    }

}