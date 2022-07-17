package com.plateer.ec1.payment.service;

import com.plateer.ec1.payment.factory.PaymentProcessorFactory;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.req.ApproveReqVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.req.CancelReqVO;
import com.plateer.ec1.payment.vo.req.NetCancelReqVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class PayService {

    private final PaymentProcessorFactory paymentProcessorFactory;

    public List<ApproveResVO> approve(@Valid ApproveReqVO approveReqVO){
        List<ApproveResVO> approveResVOList = new ArrayList<>();

        OrderInfoVO orderInfoVO = approveReqVO.getOrderInfoVO();

        for (PayInfoVO payInfoVO : approveReqVO.getPayInfoVOList()) {
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

}
