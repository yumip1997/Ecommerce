package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OrderClaim;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClaimVO {
    private OrderClaim orderClaim;
    //클레임 타입
    private String claimType;
    //주문금액
    private Long orderAmount;
    //클레임금액
    private Long claimAmount;
    //환불예정금액
    //클레임번호
    private String claimNo;
    //주문번호
    private String orderNo;
    private String productType;
    private String orderType;
}
