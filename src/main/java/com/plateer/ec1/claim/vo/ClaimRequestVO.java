package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.define.ClaimDefine;
import com.plateer.ec1.claim.enums.define.OpBnfBase;
import com.plateer.ec1.claim.enums.define.OpClmInsertBase;
import com.plateer.ec1.claim.enums.define.OpClmUpdateBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static java.util.stream.Collectors.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimRequestVO extends OrderClaimBaseVO implements Cloneable{

    @NotEmpty
    private String prdType;
    @NotNull
    private List<String> ordClmReqTypes;
    @NotNull
    private Long payAmt;
    @NotNull
    private Long cnclReqAmt;
    @NotNull
    private String paymentType;
    @NotNull
    private String mbrNo;
    @NotEmpty
    private List<@Valid ClaimGoodsInfo> claimGoodsInfoList;

    private List<ClaimDeliveryInfo> claimDeliveryInfoList;

    public boolean isPartialClaim(){
        return !cnclReqAmt.equals(payAmt);
    }

    public List<String> getOrdPrsgList(){
        return this.claimGoodsInfoList.stream()
                .map(ClaimGoodsInfo::getOrdPrgsScd)
                .collect(toList());
    }

    public List<OpClmInfo> toInsertOpClmInfoList(OpClmInsertBase opClmInsertBase, String clmNo){
        if(opClmInsertBase == null) return Collections.emptyList();

        return claimGoodsInfoList.stream()
                .map(e -> e.toInsertOpClmInfoList(opClmInsertBase, clmNo))
                .flatMap(List::stream)
                .collect(toList());
    }

    public List<OpOrdBnfRelInfo> toInsertOpOrdBnfRelInfoList(OpBnfBase opBnfBase, String clmNo){
        if(opBnfBase == null) return Collections.emptyList();

        return claimGoodsInfoList.stream()
                .map(e -> e.toInsertOpOrdBnfRelInfoList(opBnfBase, clmNo))
                .flatMap(List::stream)
                .collect(toList());
    }

    public List<OpOrdCostInfo> toInsertOpOrdCostInfoList(String clmNo){
        return Optional.ofNullable(claimDeliveryInfoList)
                .orElse(Collections.emptyList())
                .stream()
                .map(e -> e.toOpOrdCostInfo(clmNo))
                .collect(toList());
    }

    public List<OpClmInfo> toUpdateOpClmInfoList(OpClmUpdateBase updateBase, Supplier<ClaimGoodsInfo> supplier){
        return claimGoodsInfoList.stream()
                .map(e -> e.toUpdateOpClmInfoList(updateBase, supplier))
                .flatMap(List::stream)
                .collect(toList());
    }

    public List<OpOrdBnfInfo> toUpdateOpOrdBnfInfo(OpBnfBase opBnfBase){
        if(opBnfBase == null) return Collections.emptyList();

        Map<String, Integer> mapOfSumCnclAmtByBnfNo = claimGoodsInfoList.stream()
                .map(e -> e.toUpdateOpOrdBnfInfoList(opBnfBase))
                .flatMap(List::stream)
                .collect(groupingBy(OpOrdBnfInfo::getOrdBnfNo, summingInt(OpOrdBnfInfo::getOrdCnclBnfAmt)));

        return mapOfSumCnclAmtByBnfNo.entrySet().stream()
                .map(e -> OpOrdBnfInfo.of(e.getKey(), e.getValue()))
                .collect(toList());
    }

    public List<OpOrdCostInfo> toUpdateOpOrdCostInfoList(){
        return isWithdrawalReq() ? toCostInfoApplyToCancel() : Collections.emptyList();
    }

    public List<OpOrdCostInfo> toCostInfoApplyToCancel(){
        return this.claimDeliveryInfoList.stream()
                .filter(ClaimDeliveryInfo::isApply)
                .map(ClaimDeliveryInfo::toUpdateOpOrdCostInfo)
                .collect(toList());
    }

    public List<CupIssVO> toCupIssVOList(){
        if(this.isPartialClaim()) return Collections.emptyList();

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

    public boolean isWithdrawalReq(){
        return ClaimDefine.of(this).isWithrawalReq();
    }

    @Override
    public ClaimRequestVO clone() {
        try {
            return (ClaimRequestVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
