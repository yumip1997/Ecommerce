package com.plateer.ec1.payment.mapper;

import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    OrderPayInfoVO getOrderPayInfo(PaymentCancelReqVO reqVO);

}
