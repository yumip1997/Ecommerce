package com.plateer.ec1.payment.service.impl;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.service.PaymentService;
import com.plateer.ec1.payment.vo.ApproveRes;
import com.plateer.ec1.payment.vo.NetCancelReq;
import com.plateer.ec1.payment.vo.OriginOrder;
import com.plateer.ec1.payment.vo.PayInfo;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class Point implements PaymentService {

    @Override
    public ApproveRes approvePay(PayInfo payInfo) {
        log.info("포인트 승인 요청 로직을 진행한다.");
        return null;
    }

    @Override
    public void cancelPay(OriginOrder originOrder) {
        log.info("포인트 결제취소 로직을 진행한다.");
    }

    @Override
    public void netCancel(NetCancelReq netCancelReq) {
        log.info("포인트 망취소 로직을 진행한다.");
    }

    @Override
    public PaymentType getType() {
        return PaymentType.POINT;
    }
}
