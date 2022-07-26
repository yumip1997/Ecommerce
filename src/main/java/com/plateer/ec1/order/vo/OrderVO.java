package com.plateer.ec1.order.vo;

import com.plateer.ec1.payment.vo.req.ApproveReqVO;

public class OrderVO {

    public ApproveReqVO toPayApproveReqVO(){
        return ApproveReqVO.builder().build();
    }
}
