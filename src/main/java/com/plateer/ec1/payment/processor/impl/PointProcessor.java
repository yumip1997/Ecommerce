package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.utils.PaymentDataManipulator;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Component
@RequiredArgsConstructor
@Validated
public class PointProcessor implements PaymentProcessor {

    private final PaymentDataManipulator paymentDataManipulator;

    @Override
    @Transactional
    public ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
        paymentDataManipulator.insertOrderPayment(OpPayInfoModel.getInsertData(payInfoVO.getPayAmount(), orderInfoVO));
        return new ApproveResVO(payInfoVO.getPaymentType());
    }

    @Override
    @Transactional
    public void cancelPay(OrderPayInfoVO orderPayInfoVO) {
        paymentDataManipulator.manipulateCnl(orderPayInfoVO);
    }

    @Override
    public PaymentType getType() {
        return PaymentType.POINT;
    }
}
