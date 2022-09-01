package com.plateer.ec1.claim.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
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
                .cnclReqAmt(this.cnclReqAmt)
                .paymentType(this.paymentType)
                .mbrNo(this.mbrNo)
                .claimGoodsInfoList(claimGoodsInfoList)
                .claimDeliveryCostInfoList(claimDeliveryCostInfoList)
                .build();
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
