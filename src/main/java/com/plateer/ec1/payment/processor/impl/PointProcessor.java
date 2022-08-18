package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.utils.PaymentDataManipulator;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.promotion.point.service.PointService;
import com.plateer.ec1.promotion.point.vo.PointVO;
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
    private final PointService pointService;

    @Override
    @Transactional
    public ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
        String payNo = paymentDataManipulator.insertOrderPayment(OpPayInfoModel.getInsertData(payInfoVO.getPayAmount(), orderInfoVO));
        pointService.usePoint(orderInfoVO.toUsePointVO(payNo, payInfoVO.getPayAmount()));

        return new ApproveResVO(payInfoVO.getPaymentType());
    }

    @Override
    @Transactional
    public void cancelPay(OrderPayInfoVO orderPayInfoVO) {
        String payNo = paymentDataManipulator.manipulateCnl(orderPayInfoVO);
        pointService.restorePoint(orderPayInfoVO.toRestorePointVO(payNo));
    }

    @Override
    public PaymentType getType() {
        return PaymentType.POINT;
    }
}
