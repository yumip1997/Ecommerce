package com.plateer.ec1.claim.creator;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.define.OpClmInsertBase;
import com.plateer.ec1.claim.mapper.ClaimMapper;
import com.plateer.ec1.claim.vo.*;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.common.utils.JsonReaderUtil;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.order.vo.OrdClmCreationVO;
import com.plateer.ec1.resource.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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

    ClaimGoodsInfo findReqClm(OpClmInfo opClmInfo, List<ClaimGoodsInfo> reqList){
        return reqList.stream()
                .filter(req -> opClmInfo.getGoodsNoItemNo().equals(req.getOrdGoodsNo()+req.getOrdItemNo()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    ClaimDeliveryInfo findReqDvCst(OpOrdCostInfo costInfo, List<ClaimDeliveryInfo> reqList){
        return reqList.stream()
                .filter(req -> req.getOrdCstNo().equals(costInfo.getOrgOrdCstNo()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    void testInsertOpClmData(List<OpClmInfo> opClmInfoList, List<ClaimGoodsInfo> reqClmList , OpClmInsertBase opClmInsertBase){
        for(int i=0;i<opClmInfoList.size();i++){
            OpClmInfo opClmInfo = opClmInfoList.get(i);

            ClaimGoodsInfo reqClm = findReqClm(opClmInfo, reqClmList);

            //처리순번
            Assertions.assertThat(opClmInfo.getProcSeq()).isEqualTo(reqClm.getProcSeq() + 1);
            //주문클레임유형코드
            Assertions.assertThat(opClmInfo.getOrdClmTpCd()).isEqualTo(opClmInsertBase.getOrdClmTpCd().get(i));
            //주문진행상태코드
            Assertions.assertThat(opClmInfo.getOrdPrgsScd()).isEqualTo(opClmInsertBase.getOrdPrgsScd().get(i));
            //배송회수구분코드
            Assertions.assertThat(opClmInfo.getDvRvtCcd()).isEqualTo(opClmInsertBase.getDvRctCcdList().get(i));
            //배송그룹번호
            Assertions.assertThat(opClmInfo.getDvGrpNo()).isEqualTo(opClmInsertBase.getDvpGrpOperator().applyAsInt(reqClm.getDvGrpNo()));
            //이전처러순번
            Assertions.assertThat(opClmInfo.getOrgProcSeq()).isEqualTo(reqClm.getProcSeq());
            //클레임완료일시
            if(opClmInsertBase.getCmtDtimeSupplier().get() != null){
                Assertions.assertThat(opClmInfo.getOrdClmCmtDtime()).isNotNull();
            }else{
                Assertions.assertThat(opClmInfo.getOrdClmCmtDtime()).isNull();
            }
        }
    }

    void testInsertOpCostInfo(List<OpOrdCostInfo> opOrdCostInfoList){
        for (OpOrdCostInfo opOrdCostInfo : opOrdCostInfoList) {
            Assertions.assertThat(opOrdCostInfo.getAplyCcd()).isEqualTo(OPT0005Code.CANCEL.code);
        }
    }


    @Test
    void general_partial_cancel(){
        ClaimRequestVO claimRequestVO = jsonReaderUtil.getObject("/ClaimRequestPartialCnlGeneral.json", ClaimRequestVO.class);
        OrdClmCreationVO<ClaimInsertBase, ClaimUpdateBase> creationVO = claimDataCreator.createOrdClmCreationVO(claimRequestVO);

        ClaimInsertBase insertData = creationVO.getInsertData();

        //주문클레임테이블 insert 데이터
        testInsertOpClmData(insertData.getOpClmInfoList(), claimRequestVO.getClaimGoodsInfoList(), ClaimDefine.of(claimRequestVO).getOpClmInsertBase());
        //주문비용 insert 데이터
        testInsertOpCostInfo(insertData.getOpOrdCostInfoList());
        //주문혜택관계 insert 데이터

    }
}