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
import com.plateer.ec1.delivery.enums.DVP0002Code;
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
public class RATest {

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
    public void return_accpet_customer_test(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/RA_customer.json", ClaimRequestVO.class);
        List<ClaimGoodsInfo> claimGoodsInfoWithBnf = claimMapper.getClaimGoodsWithBnfList(claimRequestVO.getClaimGoodsInfoList());

        List<ClaimDeliveryCostInfo> reqDvCstList = claimRequestVO.getClaimDeliveryCostInfoList();
        List<ClaimDeliveryCostInfo> deliveryCostInfoList = CollectionUtils.isEmpty(reqDvCstList) ? Collections.emptyList() : claimMapper.getClaimDeliveryCostInfoList(reqDvCstList);

        ClaimView claimView = claimRequestVO.toClaimView(claimGoodsInfoWithBnf, deliveryCostInfoList);
        ClaimBusiness claimBusiness = ClaimBusiness.of(claimRequestVO);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO = claimDataCreator.createOrdClmCreationVO(claimView, claimBusiness, claimDataCreatorFactory.getCreators(claimBusiness));

        ClaimInsertBase insertData = ordClmCreationVO.getInsertData();
        ClaimUpdateBase updateData = ordClmCreationVO.getUpdateData();

        List<OpClmInfo> opClmInfoList = insertData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : opClmInfoList) {
            assertThat(opClmInfo.getOrdClmTpCd()).isEqualTo(OPT0003Code.RETURN_ACCEPT.code);
            assertThat(opClmInfo.getOrdPrgsScd()).isEqualTo(OPT0004Code.RETURN_ACCEPT.code);
            assertThat(opClmInfo.getDvRvtCcd()).isEqualTo(OPT0013Code.RETRIEVE.code);
            assertThat(opClmInfo.getClmNo()).isNotNull();
        }

        List<OpOrdCostInfo> opOrdCostInfoList = insertData.getOpOrdCostInfoList();
        if(opOrdCostInfoList != null){
            for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
                assertThat(opOrdCostInfo.getClmNo()).isNotNull();
                assertThat(opOrdCostInfo.getDvAmtTpCd()).isEqualTo(OPT0006Code.RETURN_FEE.code);
                assertThat(opOrdCostInfo.getAplyDvAmt()).isEqualTo(3000L);
                assertThat(opOrdCostInfo.getOrgDvAmt()).isEqualTo(3000L);
                assertThat(opOrdCostInfo.getOrgDvAmt()).isEqualTo(3000L);
                assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.APPLY.code);
                assertThat(opOrdCostInfo.getDvPlcTpCd()).isEqualTo(DVP0002Code.PAY.code);
                assertThat(opOrdCostInfo.getImtnRsnCcd()).isEqualTo(OPT0008Code.CUSTOMER.code);
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
            assertThat(opClmInfo.getRtgsCnt()).isNotZero();
        }

        List<OpOrdBnfInfo> opOrdBnfInfoList = updateData.getOpOrdBnfInfoList();
        if(opOrdBnfInfoList != null){
            for (OpOrdBnfInfo opOrdBnfInfo : opOrdBnfInfoList) {
                assertThat(opOrdBnfInfo.getOrdCnclBnfAmt()).isNotZero();
            }
        }
    }

    @Test
    public void return_accpet_company_test(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/RA_company.json", ClaimRequestVO.class);
        List<ClaimGoodsInfo> claimGoodsInfoWithBnf = claimMapper.getClaimGoodsWithBnfList(claimRequestVO.getClaimGoodsInfoList());

        List<ClaimDeliveryCostInfo> reqDvCstList = claimRequestVO.getClaimDeliveryCostInfoList();
        List<ClaimDeliveryCostInfo> deliveryCostInfoList = CollectionUtils.isEmpty(reqDvCstList) ? Collections.emptyList() : claimMapper.getClaimDeliveryCostInfoList(reqDvCstList);

        ClaimView claimView = claimRequestVO.toClaimView(claimGoodsInfoWithBnf, deliveryCostInfoList);
        ClaimBusiness claimBusiness = ClaimBusiness.of(claimRequestVO);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO = claimDataCreator.createOrdClmCreationVO(claimView, claimBusiness, claimDataCreatorFactory.getCreators(claimBusiness));

        ClaimInsertBase insertData = ordClmCreationVO.getInsertData();
        ClaimUpdateBase updateData = ordClmCreationVO.getUpdateData();

        List<OpClmInfo> opClmInfoList = insertData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : opClmInfoList) {
            assertThat(opClmInfo.getOrdClmTpCd()).isEqualTo(OPT0003Code.RETURN_ACCEPT.code);
            assertThat(opClmInfo.getOrdPrgsScd()).isEqualTo(OPT0004Code.RETURN_ACCEPT.code);
            assertThat(opClmInfo.getDvRvtCcd()).isEqualTo(OPT0013Code.RETRIEVE.code);
            assertThat(opClmInfo.getClmNo()).isNotNull();
        }

        List<OpOrdCostInfo> opOrdCostInfoList = insertData.getOpOrdCostInfoList();
        if(opOrdCostInfoList != null){
            for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
                if(OPT0005Code.APPLY.code.equals(opOrdCostInfo.getAplyCcd())){
                    assertThat(opOrdCostInfo.getClmNo()).isNotNull();
                    assertThat(opOrdCostInfo.getDvAmtTpCd()).isEqualTo(OPT0006Code.RETURN_FEE.code);
                    assertThat(opOrdCostInfo.getAplyDvAmt()).isEqualTo(0L);
                    assertThat(opOrdCostInfo.getOrgDvAmt()).isEqualTo(3000L);
                    assertThat(opOrdCostInfo.getDvBnfAmt()).isEqualTo(3000L);
                    assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.APPLY.code);
                    assertThat(opOrdCostInfo.getDvPlcTpCd()).isEqualTo(DVP0002Code.PAY.code);
                    assertThat(opOrdCostInfo.getImtnRsnCcd()).isEqualTo(OPT0008Code.COMPANY.code);
                }else{
                    assertThat(opOrdCostInfo.getClmNo()).isNotNull();
                    assertThat(opOrdCostInfo.getOrgOrdCstNo()).isNotNull();
                    assertThat(opOrdCostInfo.getDvAmtTpCd()).isEqualTo(OPT0006Code.DV_FEE.code);
                    assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.CANCEL.code);
                    assertThat(opOrdCostInfo.getDvPlcTpCd()).isEqualTo(DVP0002Code.FREE.code);
                }
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
            assertThat(opClmInfo.getRtgsCnt()).isNotZero();
        }

        List<OpOrdBnfInfo> opOrdBnfInfoList = updateData.getOpOrdBnfInfoList();
        if(opOrdBnfInfoList != null){
            for (OpOrdBnfInfo opOrdBnfInfo : opOrdBnfInfoList) {
                assertThat(opOrdBnfInfo.getOrdCnclBnfAmt()).isNotZero();
            }
        }
    }
}
