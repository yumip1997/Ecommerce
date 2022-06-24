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
public class Inicis implements PaymentService {

    @Override
    public ApproveRes approvePay(PayInfo payInfo) {
        log.info("이니시스 승인 요청 로직을 진행한다.");
        //1. 승인 요청 IF 전문을 생성한다.
        //2. 승인 요청 이력을 저장한다.
        //3. 승인 요청 IF를 호출한다.
        //4. 응답값(가상계좌 번호, TID)으로 주문결제 테이블에 승인 데이터를 저장한다.
        //5. 승인 요청 이력을 수정한다.
        return new ApproveRes();
    }

    @Override
    public void cancelPay(OriginOrder originOrder) {
        log.info("이니시스 결제취소 로직을 진행한다.");
        //1. 원 주문결제 데이터를 조회한다.
        //2. 요청으로 받은 취소 금액과 - 실제 쿼리로 조회한 취소 금액(주문금액 - 혜택금액 - 배송비)이 일치하는지 확인한다.
        //3. 주문 결제 테이블의 취소 금액, 환불 가능 금액을 업데이트 한다.

    }

    @Override
    public void netCancel(NetCancelReq netCancelReq) {
        log.info("이니시스 망취소 로직을 진행한다.");
    }

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }
}
