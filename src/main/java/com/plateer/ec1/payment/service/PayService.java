package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.factory.PaymentServiceFactory;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.req.CancelReqVO;
import com.plateer.ec1.payment.vo.req.NetCancelReqVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PaymentServiceFactory paymentServiceFactory;

    public ApproveResVO approve(PayInfoVO payInfoVO){
        PaymentService paymentService = paymentServiceFactory.get(payInfoVO.getPaymentType());
        return paymentService.approvePay(payInfoVO);
    }

    public void cancel(CancelReqVO cancelReqVO){
        PaymentService paymentService = paymentServiceFactory.get(cancelReqVO.getPaymentType());
        paymentService.cancelPay(cancelReqVO.getOriginOrderVO());
    }

    public void netCancel(NetCancelReqVO netCancelReqVO){
        PaymentService paymentService = paymentServiceFactory.get(netCancelReqVO.getPaymentType());
        paymentService.netCancel(netCancelReqVO);
    }

}
