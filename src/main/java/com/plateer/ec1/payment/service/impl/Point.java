package com.plateer.ec1.payment.service.impl;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.ApproveResVO;
import com.plateer.ec1.payment.vo.NetCancelReqVO;
import com.plateer.ec1.payment.vo.OriginOrderVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Point implements PaymentService {

    @Override
    public ApproveResVO approvePay(PayInfoVO payInfoVO) {
        log.info("포인트 승인 요청 로직을 진행한다.");
        return null;
    }

    @Override
    public void cancelPay(OriginOrderVO originOrderVO) {
        log.info("포인트 결제취소 로직을 진행한다.");
    }

    @Override
    public void netCancel(NetCancelReqVO netCancelReqVO) {
        log.info("포인트 망취소 로직을 진행한다.");
    }

    @Override
    public PaymentType getType() {
        return PaymentType.POINT;
    }
}
