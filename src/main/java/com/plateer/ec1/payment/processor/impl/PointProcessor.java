package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.mapper.PaymentTrxMapper;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointProcessor implements PaymentProcessor {

    private final PaymentTrxMapper paymentTrxMapper;

    @Override
    @Transactional
    public ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
        OpPayInfoModel model = OpPayInfoModel.getBasicInsertData(payInfoVO.getPayAmount(), orderInfoVO);
        paymentTrxMapper.insertOrderPayment(model);
        return new ApproveResVO(payInfoVO.getPaymentType());
    }

    @Override
    public void cancelPay(OrderPayInfoVO orderPayInfoVO) {
        log.info("포인트 결제취소 로직을 진행한다.");
    }

    @Override
    public PaymentType getType() {
        return PaymentType.POINT;
    }
}
