package com.plateer.ec1.payment.service;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.req.NetCancelReqVO;
import com.plateer.ec1.payment.vo.OriginOrderVO;
import com.plateer.ec1.payment.vo.PayInfoVO;

public interface PaymentService extends CustomFactory<PaymentType> {

    ApproveResVO approvePay(PayInfoVO payInfoVO);
    void cancelPay(OriginOrderVO originOrderVO);
    void netCancel(NetCancelReqVO netCancelReqVO);

}
