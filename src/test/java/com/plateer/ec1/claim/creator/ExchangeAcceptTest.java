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
public class ExchangeAcceptTest {
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
    public void exchange_accpet_customer_test(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/EA_customer.json", ClaimRequestVO.class);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO = getCreationVO(claimRequestVO);

        ClaimInsertBase insertData = ordClmCreationVO.getInsertData();
        ClaimUpdateBase updateData = ordClmCreationVO.getUpdateData();
        exchange_accept_test(insertData, updateData);

        List<OpOrdCostInfo> opOrdCostInfoList = insertData.getOpOrdCostInfoList();
        for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
            assertThat(opOrdCostInfo.getClmNo()).isNotNull();
            assertThat(opOrdCostInfo.getDvAmtTpCd()).isEqualTo(OPT0006Code.EXCHANGE_FEE.code);
            assertThat(opOrdCostInfo.getAplyDvAmt()).isEqualTo(0L);
            assertThat(opOrdCostInfo.getOrgDvAmt()).isEqualTo(3000L);
            assertThat(opOrdCostInfo.getDvBnfAmt()).isEqualTo(3000L);
            assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.APPLY.code);
            assertThat(opOrdCostInfo.getDvPlcTpCd()).isEqualTo(DVP0002Code.PAY_ON_DELIVERY.code);   //착불 결제
            assertThat(opOrdCostInfo.getImtnRsnCcd()).isEqualTo(OPT0008Code.CUSTOMER.code);         //고객 사유
        }
    }

    @Test
    public void exchange_accpet_company_test(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/EA_company.json", ClaimRequestVO.class);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO = getCreationVO(claimRequestVO);

        ClaimInsertBase insertData = ordClmCreationVO.getInsertData();
        ClaimUpdateBase updateData = ordClmCreationVO.getUpdateData();
        exchange_accept_test(insertData, updateData);

        List<OpOrdCostInfo> opOrdCostInfoList = insertData.getOpOrdCostInfoList();
        for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
            assertThat(opOrdCostInfo.getClmNo()).isNotNull();
            assertThat(opOrdCostInfo.getDvAmtTpCd()).isEqualTo(OPT0006Code.EXCHANGE_FEE.code);
            assertThat(opOrdCostInfo.getAplyDvAmt()).isEqualTo(0L);
            assertThat(opOrdCostInfo.getOrgDvAmt()).isEqualTo(3000L);
            assertThat(opOrdCostInfo.getDvBnfAmt()).isEqualTo(3000L);
            assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.APPLY.code);
            assertThat(opOrdCostInfo.getDvPlcTpCd()).isEqualTo(DVP0002Code.PAY.code);   //착불 결제
            assertThat(opOrdCostInfo.getImtnRsnCcd()).isEqualTo(OPT0008Code.COMPANY.code);  //당사
        }
    }

    private void exchange_accept_test(ClaimInsertBase insertData,  ClaimUpdateBase updateData){
        List<OpClmInfo> opClmInfoList = insertData.getOpClmInfoList();
        assertThat(opClmInfoList.size()).isEqualTo(2);

        for (OpClmInfo opClmInfo : opClmInfoList) {
            assertThat(opClmInfo.getOrdClmTpCd()).isEqualTo(OPT0003Code.EXCHANGE_ACCEPT.code);
            assertThat(opClmInfo.getOrdPrgsScd()).isEqualTo(OPT0004Code.EXCHANGE_ACCEPT.code);
            assertThat(opClmInfo.getClmNo()).isNotNull();
        }

        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = insertData.getOpOrdBnfRelInfoList();
        assertThat(opOrdBnfRelInfoList).isEmpty();

        List<OpClmInfo> updateOpClmInfoList = updateData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : updateOpClmInfoList) {
            assertThat(opClmInfo.getRtgsCnt()).isNotZero();
        }

        List<OpOrdBnfInfo> opOrdBnfInfoList = updateData.getOpOrdBnfInfoList();
        assertThat(opOrdBnfInfoList).isEmpty();
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
