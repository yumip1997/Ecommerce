package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.factory.ClaimDataCreatorFactory;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.vo.*;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.enums.*;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderCancelTest {

    @Autowired
    private ClaimMapper claimMapper;
    @Autowired
    private ClaimDataCreatorManager claimDataCreator;
    @Autowired
    private ClaimDataCreatorFactory claimDataCreatorFactory;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "claim");
    }

    @Test
    public void gnr_all_test(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/ClaimRequestAllCnclGeneral.json", ClaimRequestVO.class);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = getCreationVO(claimRequestVO);
        gnr_cnl_test(creationVO);
    }

    @Test
    public void gnr_partial_test(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/ClaimRequestPartialCnlGeneral.json", ClaimRequestVO.class);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = getCreationVO(claimRequestVO);
        gnr_cnl_test(creationVO);
    }

    @Test
    public void gnr_all_test_with_cup(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/ClaimRequestAllCnclGeneralWithCup.json", ClaimRequestVO.class);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = getCreationVO(claimRequestVO);
        gnr_cnl_test(creationVO);
    }

    @Test
    public void gnr_partial_test_with_cup(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/ClaimRequestPartialCnlGeneralWithCup.json", ClaimRequestVO.class);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = getCreationVO(claimRequestVO);
        gnr_cnl_test(creationVO);
    }

    private void gnr_cnl_test(OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO){
        ClaimInsertBase insertData = ordClmCreationVO.getInsertData();
        ClaimUpdateBase updateData = ordClmCreationVO.getUpdateData();

        List<OpClmInfo> opClmInfoList = insertData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : opClmInfoList) {
            assertThat(opClmInfo.getOrdClmTpCd()).isEqualTo(OPT0003Code.CANCEL.code);
            assertThat(opClmInfo.getOrdPrgsScd()).isEqualTo(OPT0004Code.CANCEL_COMPLETE.code);
            assertThat(opClmInfo.getClmNo()).isNotNull();
            assertThat(opClmInfo.getOrdClmCmtDtime()).isNotNull();
        }

        List<OpOrdCostInfo> opOrdCostInfoList = insertData.getOpOrdCostInfoList();
        if(opOrdCostInfoList != null){
            for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
                assertThat(opOrdCostInfo.getOrdCstNo()).isNotNull();
                assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.CANCEL.code);
            }
        }

        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = insertData.getOpOrdBnfRelInfoList();
        if(opOrdBnfRelInfoList != null){
            for (OpOrdBnfRelInfo opOrdBnfRelInfo : opOrdBnfRelInfoList) {
                assertThat(opOrdBnfRelInfo.getAplyCnclCcd()).isEqualTo(OPT0005Code.CANCEL.code);
            }
        }

        List<OpClmInfo> updateOpClmInfoList = updateData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : updateOpClmInfoList) {
            assertThat(opClmInfo.getCnclCnt()).isNotZero();
        }

        assertThat(updateData.getOpOrdCostInfoList()).isEmpty();

        List<OpOrdBnfInfo> opOrdBnfInfoList = updateData.getOpOrdBnfInfoList();
        if(opOrdBnfInfoList != null){
            for (OpOrdBnfInfo opOrdBnfInfo : opOrdBnfInfoList) {
                assertThat(opOrdBnfInfo.getOrdCnclBnfAmt()).isNotZero();
            }
        }
    }

    private OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> getCreationVO(ClaimRequestVO claimRequestVO){
        List<ClaimGoodsInfo> claimGoodsInfoWithBnf = claimMapper.getClaimGoodsWithBnfList(claimRequestVO.getClaimGoodsInfoList());

        List<ClaimDeliveryCostInfo> reqDvCstList = claimRequestVO.getClaimDeliveryCostInfoList();
        List<ClaimDeliveryCostInfo> deliveryCostInfoList = CollectionUtils.isEmpty(reqDvCstList) ? Collections.emptyList() : claimMapper.getClaimDeliveryCostInfoList(reqDvCstList);

        ClaimView claimView = claimRequestVO.toClaimView(claimGoodsInfoWithBnf, deliveryCostInfoList);
        ClaimBusiness claimBusiness = ClaimBusiness.of(claimRequestVO);
        return  claimDataCreator.createOrdClmCreationVO(claimView, claimBusiness, claimDataCreatorFactory.getCreators(claimBusiness));
    }

}
