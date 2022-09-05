package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.ClaimBusiness;
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
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RWTest {

    @Autowired
    private ClaimMapper claimMapper;
    private ClaimDataCreator claimDataCreator;
    private JsonReaderUtil jsonReaderUtil;

    @BeforeEach
    void init(){
        jsonReaderUtil = new JsonReaderUtil(TestConstants.TEST_FILE_PATH + "claim");
        claimDataCreator = new ClaimDataCreator(claimMapper);
    }

    @Test
    public void return_withdrawal_company_test(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/RW_company.json", ClaimRequestVO.class);
        List<ClaimGoodsInfo> claimGoodsInfoWithBnf = claimMapper.getClaimGoodsWithBnfList(claimRequestVO.getClaimGoodsInfoList());

        List<ClaimDeliveryCostInfo> reqDvCstList = claimRequestVO.getClaimDeliveryCostInfoList();
        List<ClaimDeliveryCostInfo> deliveryCostInfoList = CollectionUtils.isEmpty(reqDvCstList) ? Collections.emptyList() : claimMapper.getClaimDeliveryCostInfoList(reqDvCstList);

        ClaimView claimView = claimRequestVO.toClaimView(claimGoodsInfoWithBnf, deliveryCostInfoList);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO = claimDataCreator.createOrdClmCreationVO(ClaimBusiness.of(claimRequestVO), claimView);

        ClaimInsertBase insertData = ordClmCreationVO.getInsertData();
        ClaimUpdateBase updateData = ordClmCreationVO.getUpdateData();

        List<OpClmInfo> opClmInfoList = insertData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : opClmInfoList) {
            assertThat(opClmInfo.getOrdClmTpCd()).isEqualTo(OPT0003Code.RETURN_WITHDRAWAL.code);
            assertThat(opClmInfo.getOrdPrgsScd()).isEqualTo(OPT0004Code.RETURN_WITHDRAWAL.code);
            assertThat(opClmInfo.getDvRvtCcd()).isEqualTo(OPT0013Code.RETRIEVE.code);
            assertThat(opClmInfo.getClmNo()).isNotNull();
        }

        List<OpOrdCostInfo> opOrdCostInfoList = insertData.getOpOrdCostInfoList();
        if(opOrdCostInfoList != null){
            for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
                //원 접수건에 대한 취소 ROW
                if(OPT0005Code.CANCEL.code.equals(opOrdCostInfo.getAplyCcd())){
                    assertThat(opOrdCostInfo.getClmNo()).isNotNull();
                    assertThat(opOrdCostInfo.getDvAmtTpCd()).isEqualTo(OPT0006Code.RETURN_FEE.code);
                    assertThat(opOrdCostInfo.getAplyDvAmt()).isEqualTo(0L);
                    assertThat(opOrdCostInfo.getOrgDvAmt()).isEqualTo(3000L);
                    assertThat(opOrdCostInfo.getDvBnfAmt()).isEqualTo(3000L);
                    assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.CANCEL.code);
                    assertThat(opOrdCostInfo.getDvPlcTpCd()).isEqualTo(DVP0002Code.PAY.code);
                    assertThat(opOrdCostInfo.getImtnRsnCcd()).isEqualTo(OPT0008Code.COMPANY.code);
                }else{ //원 배송비 환불에 대한 적용 ROW
                    assertThat(opOrdCostInfo.getClmNo()).isNotNull();
                    assertThat(opOrdCostInfo.getOrgOrdCstNo()).isNotNull();
                    assertThat(opOrdCostInfo.getDvAmtTpCd()).isEqualTo(OPT0006Code.DV_FEE.code);
                    assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.APPLY.code);
                    assertThat(opOrdCostInfo.getDvPlcTpCd()).isEqualTo(DVP0002Code.FREE.code);
                }
            }
        }

        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = insertData.getOpOrdBnfRelInfoList();
        if(opOrdBnfRelInfoList != null){
            for (OpOrdBnfRelInfo opOrdBnfRelInfo : opOrdBnfRelInfoList) {
                assertThat(opOrdBnfRelInfo.getAplyCnclCcd()).isEqualTo(OPT0005Code.APPLY.code);
            }
        }

        List<OpClmInfo> updateOpClmInfoList = updateData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : updateOpClmInfoList) {
            ClaimGoodsInfo claimGoodsInfo = getClaimGoodsInfo(opClmInfo, claimView.getClaimGoodsInfoList());
            if(claimGoodsInfo != null){
                //접수 건에 대해 취소 수량
                assertThat(opClmInfo.getCnclCnt()).isEqualTo(claimGoodsInfo.getOrdCnt());
            }else{
                //새로 조회한 원 주문 건에 대해 반품 수량 0
                assertThat(opClmInfo.getRtgsCnt()).isZero();
            }

        }

        //반품 접수건에 대한 취소 배송비
        List<OpOrdCostInfo> updateOpOrdCost = updateData.getOpOrdCostInfoList();
        for (OpOrdCostInfo opOrdCostInfo : updateOpOrdCost) {
            assertThat(opOrdCostInfo.getCnclDvAmt()).isEqualTo(0L);
        }

        List<OpOrdBnfInfo> opOrdBnfInfoList = updateData.getOpOrdBnfInfoList();
        if(opOrdBnfInfoList != null){
            for (OpOrdBnfInfo opOrdBnfInfo : opOrdBnfInfoList) {
                assertThat(opOrdBnfInfo.getOrdCnclBnfAmt()).isZero();
            }
        }
    }

    private ClaimGoodsInfo getClaimGoodsInfo(OpClmInfo opClmInfo, List<ClaimGoodsInfo> claimGoodsInfoList){
        return claimGoodsInfoList.stream()
                .filter(e -> e.getOrdNo().equals(opClmInfo.getOrdNo()) && e.getOrdSeq().equals(opClmInfo.getOrdSeq()) && e.getProcSeq().equals(opClmInfo.getProcSeq()))
                .findFirst()
                .orElse(null);
    }
    @Test
    public void return_withdrawal_customer_test(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/RW_customer.json", ClaimRequestVO.class);
        List<ClaimGoodsInfo> claimGoodsInfoWithBnf = claimMapper.getClaimGoodsWithBnfList(claimRequestVO.getClaimGoodsInfoList());

        List<ClaimDeliveryCostInfo> reqDvCstList = claimRequestVO.getClaimDeliveryCostInfoList();
        List<ClaimDeliveryCostInfo> deliveryCostInfoList = CollectionUtils.isEmpty(reqDvCstList) ? Collections.emptyList() : claimMapper.getClaimDeliveryCostInfoList(reqDvCstList);

        ClaimView claimView = claimRequestVO.toClaimView(claimGoodsInfoWithBnf, deliveryCostInfoList);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> ordClmCreationVO = claimDataCreator.createOrdClmCreationVO(ClaimBusiness.of(claimRequestVO), claimView);

        ClaimInsertBase insertData = ordClmCreationVO.getInsertData();
        ClaimUpdateBase updateData = ordClmCreationVO.getUpdateData();

        List<OpClmInfo> opClmInfoList = insertData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : opClmInfoList) {
            assertThat(opClmInfo.getOrdClmTpCd()).isEqualTo(OPT0003Code.RETURN_WITHDRAWAL.code);
            assertThat(opClmInfo.getOrdPrgsScd()).isEqualTo(OPT0004Code.RETURN_WITHDRAWAL.code);
            assertThat(opClmInfo.getDvRvtCcd()).isEqualTo(OPT0013Code.RETRIEVE.code);
            assertThat(opClmInfo.getClmNo()).isNotNull();
        }

        List<OpOrdCostInfo> opOrdCostInfoList = insertData.getOpOrdCostInfoList();
        if(opOrdCostInfoList != null){
            for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
                //원 접수건에 대한 취소 ROW
                if(OPT0005Code.CANCEL.code.equals(opOrdCostInfo.getAplyCcd())){
                    assertThat(opOrdCostInfo.getClmNo()).isNotNull();
                    assertThat(opOrdCostInfo.getDvAmtTpCd()).isEqualTo(OPT0006Code.RETURN_FEE.code);
                    assertThat(opOrdCostInfo.getAplyDvAmt()).isEqualTo(3000L);
                    assertThat(opOrdCostInfo.getOrgDvAmt()).isEqualTo(3000L);
                    assertThat(opOrdCostInfo.getDvBnfAmt()).isEqualTo(0L);
                    assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.CANCEL.code);
                    assertThat(opOrdCostInfo.getDvPlcTpCd()).isEqualTo(DVP0002Code.PAY.code);
                    assertThat(opOrdCostInfo.getImtnRsnCcd()).isEqualTo(OPT0008Code.CUSTOMER.code);
                }
            }
        }

        List<OpOrdBnfRelInfo> opOrdBnfRelInfoList = insertData.getOpOrdBnfRelInfoList();
        if(opOrdBnfRelInfoList != null){
            for (OpOrdBnfRelInfo opOrdBnfRelInfo : opOrdBnfRelInfoList) {
                assertThat(opOrdBnfRelInfo.getAplyCnclCcd()).isEqualTo(OPT0005Code.APPLY.code);
            }
        }

        List<OpClmInfo> updateOpClmInfoList = updateData.getOpClmInfoList();
        for (OpClmInfo opClmInfo : updateOpClmInfoList) {
            ClaimGoodsInfo claimGoodsInfo = getClaimGoodsInfo(opClmInfo, claimView.getClaimGoodsInfoList());
            if(claimGoodsInfo != null){
                //접수 건에 대해 취소 수량
                assertThat(opClmInfo.getCnclCnt()).isEqualTo(claimGoodsInfo.getOrdCnt());
            }else{
                //새로 조회한 원 주문 건에 대해 반품 수량 0
                assertThat(opClmInfo.getRtgsCnt()).isZero();
            }

        }

        //반품 접수건에 대한 취소 배송비
        List<OpOrdCostInfo> updateOpOrdCost = updateData.getOpOrdCostInfoList();
        for (OpOrdCostInfo opOrdCostInfo : updateOpOrdCost) {
            assertThat(opOrdCostInfo.getCnclDvAmt()).isEqualTo(0L);
        }

        List<OpOrdBnfInfo> opOrdBnfInfoList = updateData.getOpOrdBnfInfoList();
        if(opOrdBnfInfoList != null){
            for (OpOrdBnfInfo opOrdBnfInfo : opOrdBnfInfoList) {
                assertThat(opOrdBnfInfo.getOrdCnclBnfAmt()).isZero();
            }
        }
    }
}
