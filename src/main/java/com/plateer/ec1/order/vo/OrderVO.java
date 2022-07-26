package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.vo.base.OrderClaimBaseVO;
import com.plateer.ec1.payment.vo.req.ApproveReqVO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderVO extends OrderClaimBaseVO {

    public OrdClmCreationVO<OrderVO, Object> toOrdClmCreationVO(){
        OrdClmCreationVO<OrderVO, Object> creationVO = new OrdClmCreationVO<>();
        creationVO.setOrdNo(this.getOrdNo());
        creationVO.setClmNo(this.getClmNo());
        creationVO.setInsertData(this);
        return creationVO;
    }

    public ApproveReqVO toPayApproveReqVO(){
        return ApproveReqVO.builder().build();
    }
}
