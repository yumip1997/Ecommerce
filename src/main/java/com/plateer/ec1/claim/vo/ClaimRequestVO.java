package com.plateer.ec1.claim.vo;

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

import static java.util.stream.Collectors.toList;

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

    @Override
    public ClaimRequestVO clone() {
        try {
            return (ClaimRequestVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
