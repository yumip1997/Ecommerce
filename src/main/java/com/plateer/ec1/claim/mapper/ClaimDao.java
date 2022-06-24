package com.plateer.ec1.claim.mapper;

import com.plateer.ec1.claim.vo.ClaimDto;
import com.plateer.ec1.common.model.order.OrderBenefit;
import com.plateer.ec1.common.model.order.OrderBenefitRelation;
import com.plateer.ec1.common.model.order.OrderClaim;
import com.plateer.ec1.common.model.order.OrderCost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClaimDao {

    OrderClaim selectClaim(ClaimDto claimDto);

    void insertOrderClaim(List<OrderClaim> orderClaimList);
    void insertOrderCost(List<OrderCost> orderCostList);
    void insertOrderBenefitRelation(OrderBenefitRelation orderBenefitRelation);

    void updateOrderClaim(OrderClaim orderClaim);
    void updateOrgOrderCnt(OrderClaim orderClaim);
    void updateOrderBenefit(OrderBenefit orderBenefit);


}
