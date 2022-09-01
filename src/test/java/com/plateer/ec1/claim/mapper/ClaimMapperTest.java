package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.claim.vo.ClaimDeliveryCostInfo;
import com.plateer.ec1.claim.vo.ClaimGoodsInfo;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.resource.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

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
        List<ClaimGoodsInfo> claimGoodsWithBnfList = claimMapper.getClaimGoodsWithBnfList(object.getClaimGoodsInfoList());
        List<ClaimDeliveryCostInfo> dvCstList = object.getClaimDeliveryCostInfoList();
        List<ClaimDeliveryCostInfo> list = CollectionUtils.isEmpty(dvCstList) ? Collections.emptyList() : claimMapper.getClaimDeliveryCostInfoList(dvCstList);

        Assertions.assertThat(claimGoodsWithBnfList.size()).isEqualTo(object.getClaimGoodsInfoList().size());
        Assertions.assertThat(list.size()).isEqualTo(object.getClaimDeliveryCostInfoList().size());
    }

    @Test
    public void gnr_partial_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestPartialCnlGeneral.json", ClaimRequestVO.class);
        List<ClaimGoodsInfo> claimGoodsWithBnfList = claimMapper.getClaimGoodsWithBnfList(object.getClaimGoodsInfoList());
        List<ClaimDeliveryCostInfo> dvCstList = object.getClaimDeliveryCostInfoList();
        List<ClaimDeliveryCostInfo> list = CollectionUtils.isEmpty(dvCstList) ? Collections.emptyList() : claimMapper.getClaimDeliveryCostInfoList(dvCstList);

        Assertions.assertThat(claimGoodsWithBnfList.size()).isEqualTo(object.getClaimGoodsInfoList().size());
        Assertions.assertThat(list.size()).isEqualTo(object.getClaimDeliveryCostInfoList().size());
    }

}