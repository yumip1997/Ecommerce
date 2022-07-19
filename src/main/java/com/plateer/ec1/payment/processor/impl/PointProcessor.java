package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.PayCancelInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PointProcessor implements PaymentProcessor {

    @Override
    public ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
        log.info("포인트 승인 요청 로직을 진행한다.");
        return null;
    }

    @Override
    public void cancelPay(PayCancelInfoVO payCancelInfoVO) {
        log.info("포인트 결제취소 로직을 진행한다.");
    }

    @Override
    public PaymentType getType() {
        return PaymentType.POINT;
    }
}
