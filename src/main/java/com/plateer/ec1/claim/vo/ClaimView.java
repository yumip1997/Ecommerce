package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.order.vo.base.OrderBenefitBaseVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimView extends OrderClaimBaseVO implements Cloneable{

    private List<ClaimGoodsInfo> claimGoodsInfoList;
    private List<ClaimDeliveryCostInfo> claimDeliveryCostInfoList;

    @Builder
    public ClaimView(List<ClaimGoodsInfo> claimGoodsInfoList, List<ClaimDeliveryCostInfo> claimDeliveryCostInfoList,
                     String ordNo, String clmNo){
        super(ordNo, clmNo);
        this.claimGoodsInfoList = claimGoodsInfoList;
        this.claimDeliveryCostInfoList = claimDeliveryCostInfoList;
    }

    public List<OrderBenefitBaseVO> getOrderBenefitBaseVOList(){
        return this.getClaimGoodsInfoList().stream()
                .map(ClaimGoodsInfo::getBenefitBaseVOList)
                .flatMap(List::stream)
                .collect(toList());
    }

    public ClaimValidationVO toClaimValidationVO(ClaimBusiness claimBusiness){
        return ClaimValidationVO.builder()
                .prdType(claimBusiness.getPrdTypeStr())
                .validOrdPrgs(claimBusiness.getValidOrdPrgs())
                .claimView(this)
                .build();
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
