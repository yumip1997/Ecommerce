package com.plateer.ec1.payment.mapper;

import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    //TODO 쿼리 수정하기 - 주문상품, 회원하고도 join해야 할 듯?
    OrderPayInfoVO getOrderPayInfo(PaymentCancelReqVO reqVO);
}
