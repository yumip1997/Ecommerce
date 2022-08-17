package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.define.OpClmBase;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

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
    @NotNull
    private String mbrNo;
    @NotEmpty
    private List<@Valid ClaimGoodsInfo> claimGoodsInfoList;

    private List<ClaimDeliveryInfo> claimDeliveryInfoList;

    public List<OpClmInfo> toInsertOpClmInfoList(OpClmBase opClmBase, String clmNo){
        return claimGoodsInfoList.stream()
                .map(e -> e.toInsertOpClmInfoList(opClmBase, clmNo))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<OpOrdBnfRelInfo> toInsertOpOrdBnfRelInfoList(String clmNo){
        return claimGoodsInfoList.stream()
                .map(e -> e.toOpOrdBnfRelInfoList(clmNo))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    //TODO NULL 체크
    public List<OpOrdCostInfo> toInsertOpOrdCostInfoList(String clmNo){
        return claimDeliveryInfoList.stream()
                .map(e -> e.toOpOrdCostInfo(clmNo))
                .collect(Collectors.toList());
    }

    public List<CupIssVO> toCupIssVOList(){
        return claimGoodsInfoList.stream()
                .map(e -> e.toCupIssVOList(this.mbrNo))
                .flatMap(List::stream)
                .collect(Collectors.toList());
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
