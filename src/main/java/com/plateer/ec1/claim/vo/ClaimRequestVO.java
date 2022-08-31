package com.plateer.ec1.claim.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    private Long cnclReqAmt;
    @NotNull
    private String paymentType;
    @NotNull
    private String mbrNo;
    @NotEmpty
    private List<@Valid ClaimGoodsInfo> claimGoodsInfoList;

    private List<ClaimDeliveryInfo> claimDeliveryInfoList;

    public List<String> getOrdPrsgList(){
        return this.claimGoodsInfoList.stream()
                .map(ClaimGoodsInfo::getOrdPrgsScd)
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
