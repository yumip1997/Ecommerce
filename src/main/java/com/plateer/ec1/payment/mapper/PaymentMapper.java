package com.plateer.ec1.payment.mapper;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {

    OpPayInfoModel getOpPayInfoByOrdNo(String ordNo);
}
