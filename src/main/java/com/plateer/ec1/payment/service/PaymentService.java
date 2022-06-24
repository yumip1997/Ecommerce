package com.plateer.ec1.payment.service;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.ApproveRes;
import com.plateer.ec1.payment.vo.NetCancelReq;
import com.plateer.ec1.payment.vo.OriginOrder;
import com.plateer.ec1.payment.vo.PayInfo;

public interface PaymentService extends CustomFactory<PaymentType> {

    ApproveRes approvePay(PayInfo payInfo);
    void cancelPay(OriginOrder originOrder);
    void netCancel(NetCancelReq netCancelReq);

}
