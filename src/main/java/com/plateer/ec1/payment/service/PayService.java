package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.factory.PaymentServiceFactory;
import com.plateer.ec1.payment.vo.ApproveRes;
import com.plateer.ec1.payment.vo.CancelReq;
import com.plateer.ec1.payment.vo.NetCancelReq;
import com.plateer.ec1.payment.vo.PayInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PaymentServiceFactory paymentServiceFactory;

    public ApproveRes approve(PayInfo payInfo){
        PaymentService paymentService = paymentServiceFactory.getPaymentService(payInfo.getPaymentType());
        return paymentService.approvePay(payInfo);
    }

    public void cancel(CancelReq cancelReq){
        PaymentService paymentService = paymentServiceFactory.getPaymentService(cancelReq.getPaymentType());
        paymentService.cancelPay(cancelReq.getOriginOrder());
    }

    public void netCancel(NetCancelReq netCancelReq){
        PaymentService paymentService = paymentServiceFactory.getPaymentService(netCancelReq.getPaymentType());
        paymentService.netCancel(netCancelReq);
    }

}
