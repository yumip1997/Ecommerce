package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OrderBenefit;
import com.plateer.ec1.common.model.order.OrderClaim;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClaimUpdateBase {

    OrderClaim orgOrderClaim;
    OrderClaim orderClaim;
    OrderBenefit orderBenefit;
}
