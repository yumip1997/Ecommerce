package com.plateer.ec1.claim.vo;

import com.plateer.ec1.order.vo.OrderProductVO;
import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.order.vo.req.OrderRequestVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ClaimRequestVO extends OrderClaimBaseVO implements Cloneable{

    private String prdType;
    private String claimReqType;
    private List<ClaimBaseVO> claimBaseVOList;
    private List<OrderProductVO> orderProductVOList;

    @Override
    public ClaimRequestVO clone() {
        try {
            return (ClaimRequestVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
