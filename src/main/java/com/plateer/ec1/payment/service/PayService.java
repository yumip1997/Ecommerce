package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.factory.PaymentProcessorFactory;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.req.CancelReqVO;
import com.plateer.ec1.payment.vo.req.NetCancelReqVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayService {

    private final PaymentProcessorFactory paymentProcessorFactory;

    public List<ApproveResVO> approve(OrderInfoVO orderInfoVO, List<PayInfoVO> payInfoVOList){
        List<ApproveResVO> approveResVOList = new ArrayList<>();

        for (PayInfoVO payInfoVO : payInfoVOList) {
            PaymentProcessor paymentProcessor = paymentProcessorFactory.get(payInfoVO.getPaymentType());
            ApproveResVO approveResVO = paymentProcessor.approvePay(orderInfoVO, payInfoVO);
            approveResVOList.add(approveResVO);
        }

        return approveResVOList;
    }

    public void cancel(CancelReqVO cancelReqVO){
        PaymentProcessor paymentProcessor = paymentProcessorFactory.get(cancelReqVO.getPaymentType());
        paymentProcessor.cancelPay(cancelReqVO.getOriginOrderVO());
    }

    public void netCancel(NetCancelReqVO netCancelReqVO){
        PaymentProcessor paymentProcessor = paymentProcessorFactory.get(netCancelReqVO.getPaymentType());
        paymentProcessor.netCancel(netCancelReqVO);
    }

}
