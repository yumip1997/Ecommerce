package com.plateer.ec1.payment.mapper;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentTrxMapper {

    //TODO List로 변경
    void insertOrderPayment(OpPayInfoModel opPayInfoModel);

    void updateOrderPayment(OpPayInfoModel opPayInfoModel);

}
