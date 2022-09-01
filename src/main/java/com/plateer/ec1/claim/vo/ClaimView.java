package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimView extends OrderClaimBaseVO implements Cloneable{

    private Long cnclReqAmt;
    private String paymentType;
    private String mbrNo;

    private List<ClaimGoodsInfo> claimGoodsInfoList;
    private List<ClaimDeliveryCostInfo> claimDeliveryCostInfoList;

    private List<OpClmInfo> createdOpClmInfoList;
    private Map<String, List<ClaimGoodsInfo>> mapByOrdNoDvGrpNo;
    private Map<String, List<OpClmInfo>> mapByOrdNoOrdSeqOrgProcSeq;

    //TODO 결제정보 추가 예정

    public Map<String, List<ClaimGoodsInfo>> getMapByOrdNoDvGrpNo(){
        if(this.mapByOrdNoDvGrpNo == null){
            this.mapByOrdNoDvGrpNo = makeMapByOrdNoDvGrpNo();
        }
        return this.mapByOrdNoDvGrpNo;
    }

    private Map<String, List<ClaimGoodsInfo>> makeMapByOrdNoDvGrpNo(){
        return this.getClaimGoodsInfoList().stream()
                .collect(Collectors.groupingBy(ClaimGoodsInfo::getOrdNoDvGrpNo));
    }

    public Map<String, List<OpClmInfo>> getMapByOrdNoOrdSeqOrgProcSeq(){
        if(this.mapByOrdNoOrdSeqOrgProcSeq == null){
            this.mapByOrdNoOrdSeqOrgProcSeq = makeMapByOrdNoOrdSeqOrgProcSeq();
        }
        return this.mapByOrdNoOrdSeqOrgProcSeq;
    }

    private Map<String, List<OpClmInfo>> makeMapByOrdNoOrdSeqOrgProcSeq(){
        return this.getCreatedOpClmInfoList().stream()
                .collect(Collectors.groupingBy(OpClmInfo::getOrdNoOrdSeqOrgProcSeq));
    }

    public List<CupIssVO> toCupIssVOList(){
        return claimGoodsInfoList.stream()
                .map(e -> e.toCupIssVOList(this.mbrNo))
                .flatMap(List::stream)
                .collect(toList());
    }

    public PaymentCancelReqVO toPaymentCancelReqVO(){
        return PaymentCancelReqVO.builder()
                .paymentType(PaymentType.of(this.paymentType))
                .ordNo(this.getOrdNo())
                .cnclReqAmt(this.cnclReqAmt)
                .build();
    }

    public List<OrderBenefitBaseVO> getOrderBenefitBaseVOList(){
        return this.getClaimGoodsInfoList().stream()
                .map(ClaimGoodsInfo::getBenefitBaseVOList)
                .flatMap(List::stream)
                .collect(toList());
    }

    @Override
    public ClaimView clone() {
        try {
            return (ClaimView) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
