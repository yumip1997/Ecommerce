package com.plateer.ec1.claim.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimRequestVO extends OrderClaimBaseVO implements Cloneable{

    @NotNull
    private String prdType;
    @NotNull
    private String claimReqType;
    @NotNull
    private Long cnclReqAmt;
    @NotEmpty
    private List<@Valid ClaimGoodsInfo> claimGoodsInfoList;
    @NotEmpty
    private List<@Valid ClaimDeliveryInfo> claimDeliveryInfoList;

    @Override
    public ClaimRequestVO clone() {
        try {
            return (ClaimRequestVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
