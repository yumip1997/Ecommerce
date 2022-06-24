package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OrderBenefitRelation;
import com.plateer.ec1.common.model.order.OrderClaim;
import com.plateer.ec1.common.model.order.OrderCost;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClaimInsertBase {

    List<OrderClaim> orderClaimList;

    List<OrderCost> orderCostList;

    OrderBenefitRelation orderBenefitRelation;

}
