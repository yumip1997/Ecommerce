package com.plateer.ec1.payment.service;

import com.plateer.ec1.common.aop.log.annotation.LogTrace;
import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.factory.PaymentProcessorFactory;
import com.plateer.ec1.payment.mapper.PaymentMapper;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.req.ApproveReqVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.req.PaymentCancelReqVO;
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
@LogTrace
public class PayService {

    private final PaymentProcessorFactory paymentProcessorFactory;
    private final PaymentMapper paymentMapper;

    public List<ApproveResVO> approve(@Valid ApproveReqVO approveReqVO){
        List<ApproveResVO> approveResVOList = new ArrayList<>();

        OrderInfoVO orderInfoVO = approveReqVO.getOrderInfoVO();

        for (PayInfoVO payInfoVO : approveReqVO.getPayInfoVOList()) {
            PaymentProcessor paymentProcessor = paymentProcessorFactory.get(payInfoVO.getPaymentType());

            orderInfoVO.setPaymentBusiness(PaymentBusiness.of(payInfoVO.getPaymentType(), payInfoVO.getMethodType()));
            ApproveResVO approveResVO = paymentProcessor.approvePay(orderInfoVO, payInfoVO);
            approveResVOList.add(approveResVO);
        }

        return approveResVOList;
    }

    public void cancel(PaymentCancelReqVO paymentCancelReqVO){
        PaymentProcessor paymentProcessor = paymentProcessorFactory.get(paymentCancelReqVO.getPaymentType());
        OrderPayInfoVO orderPayInfoVO= paymentMapper.getOrderPayInfo(paymentCancelReqVO);
        paymentProcessor.cancelPay(orderPayInfoVO);
    }

}
