package com.plateer.ec1.claim.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimRequestVO extends OrderClaimBaseVO implements Cloneable{

    @NotEmpty
    private String prdType;
    @NotNull
    private List<String> ordClmReqTypes;
    @NotNull
    private Long cnclReqAmt;
    @NotNull
    private String paymentType;
    @NotNull
    private String mbrNo;
    @NotEmpty
    private List<@Valid ClaimGoodsInfo> claimGoodsInfoList;
    @NotNull
    private List<@Valid ClaimDeliveryCostInfo> claimDeliveryCostInfoList;

    public List<String> getOrdPrsgList(){
        return this.claimGoodsInfoList.stream()
                .map(ClaimGoodsInfo::getOrdPrgsScd)
                .collect(toList());
    }

    public ClaimView toClaimView(List<ClaimGoodsInfo> claimGoodsInfoList, List<ClaimDeliveryCostInfo> claimDeliveryCostInfoList){
        return ClaimView.builder()
                .claimGoodsInfoList(claimGoodsInfoList)
                .claimDeliveryCostInfoList(claimDeliveryCostInfoList)
                .build();
    }

    public PaymentCancelReqVO toPaymentCancelReqVO(String clmNo){
        return PaymentCancelReqVO.builder()
                .paymentType(PaymentType.of(this.paymentType))
                .ordNo(this.getOrdNo())
                .clmNo(clmNo)
                .cnclReqAmt(this.cnclReqAmt)
                .build();
    }

    public List<CupIssVO> toCupIssVOList(List<Long> cpnIssNoList){
        return cpnIssNoList.stream()
                .map(e -> CupIssVO.builder()
                        .cpnIssNo(e)
                        .ordNo(this.getOrdNo())
                        .mbrNo(this.mbrNo)
                        .build())
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
