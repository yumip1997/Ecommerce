package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.claim.vo.ClaimView;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.enums.OPT0003Code;
import com.plateer.ec1.order.enums.OPT0004Code;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.resource.TestConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClaimDataCreatorTest {

    @Autowired
    private ClaimMapper claimMapper;
    private ClaimDataCreator claimDataCreator;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "claim");
        claimDataCreator = new ClaimDataCreator(claimMapper);
    }

    void gnr_cnl_test(OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO){
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

        assertThat(updateData.getOpOrdCostInfoList()).isNull();

        List<OpOrdBnfInfo> opOrdBnfInfoList = updateData.getOpOrdBnfInfoList();
        if(opOrdBnfInfoList != null){
            for (OpOrdBnfInfo opOrdBnfInfo : opOrdBnfInfoList) {
                assertThat(opOrdBnfInfo.getOrdCnclBnfAmt()).isNotZero();
            }
        }
    }

    @Test
    public void gnr_all_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestAllCnclGeneral.json", ClaimRequestVO.class);
        ClaimView claimView = claimMapper.getClaimView(object);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO = claimDataCreator.createOrdClmCreationVO(object, claimView);
        gnr_cnl_test(ordClmCreationVO);
    }

    @Test
    public void gnr_partial_test(){
        ClaimRequestVO object = jsonReaderUtil.getObject("/ClaimRequestPartialCnlGeneral.json", ClaimRequestVO.class);
        ClaimView claimView = claimMapper.getClaimView(object);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO = claimDataCreator.createOrdClmCreationVO(object, claimView);
        gnr_cnl_test(ordClmCreationVO);
    }

}