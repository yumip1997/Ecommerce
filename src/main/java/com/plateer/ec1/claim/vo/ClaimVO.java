package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.enums.OrderClaimBaseCode;
import com.plateer.ec1.common.model.order.OrderClaim;
import com.plateer.ec1.order.service.OrderService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class ClaimVO {
    private OrderClaim orderClaim;
    //클레임 타입
    private String claimType;
    private String productType;
    private String orderType;
    private String claimNo;
}
