package com.plateer.ec1.claim.helper;

import com.plateer.ec1.claim.vo.ClaimVO;
import com.plateer.ec1.claim.vo.ClaimInsertBase;
import com.plateer.ec1.claim.vo.ClaimUpdateBase;
import com.plateer.ec1.common.model.order.OrderClaim;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ClaimDataCreator {

    public static ClaimInsertBase makeClaimInsertBase(ClaimVO claimVO) {
        List<OrderClaim> insertOrderClaimList= claimVO.getOrderClaim().getInsertOrderClaim(1);


        return ClaimInsertBase.builder().orderClaimList(insertOrderClaimList).build();
    }

    public static ClaimUpdateBase makeClaimUpadateBase(ClaimVO claimVO){
        OrderClaim updateOrderClaim = claimVO.getOrderClaim().getUpdateOrderClaim();

        return ClaimUpdateBase.builder().orderClaim(updateOrderClaim).build();
    }


}
