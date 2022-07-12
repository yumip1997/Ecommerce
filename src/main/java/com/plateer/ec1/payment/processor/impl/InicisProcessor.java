package com.plateer.ec1.payment.processor.impl;

import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.mapper.PaymentTrxMapper;
import com.plateer.ec1.payment.processor.PaymentProcessor;
import com.plateer.ec1.payment.utils.InicisApiCallHelper;
import com.plateer.ec1.payment.utils.InicisApiReqMaker;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.req.VacctSeqReqVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.req.NetCancelReqVO;
import com.plateer.ec1.payment.vo.OriginOrderVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import com.plateer.ec1.payment.vo.res.VacctSeqResVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

//TODO 가상계좌 PAYMENTSERVICE로 분리?
@Component
@Slf4j
@RequiredArgsConstructor
public class InicisProcessor implements PaymentProcessor {

    private final InicisApiReqMaker inicisApiReqMaker;
    private final InicisApiCallHelper inicisApiCallHelper;
    private final PaymentTrxMapper paymentTrxMapper;

    @Override
    public ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO) {
        VacctSeqReqVO reqVO = inicisApiReqMaker.makeVacctSeqReqVO(orderInfoVO, payInfoVO);
        VacctSeqResVO resVO = inicisApiCallHelper.callVacctSeq(reqVO);

        paymentTrxMapper.insertOrderPayment(OpPayInfoModel.getInsertData(reqVO, resVO));

        return new ApproveResVO(payInfoVO.getPaymentType(), resVO.getAblePartialCancelYn());
    }

    @Override
    public void cancelPay(OriginOrderVO originOrderVO) {
        log.info("이니시스 결제취소 로직을 진행한다.");
        //1. 원 주문결제 데이터를 조회한다.
        //2. 요청으로 받은 취소 금액과 - 실제 쿼리로 조회한 취소 금액(주문금액 - 혜택금액 - 배송비)이 일치하는지 확인한다.
        //3. 주문 결제 테이블의 취소 금액, 환불 가능 금액을 업데이트 한다.
    }

    @Override
    public void netCancel(NetCancelReqVO netCancelReqVO) {
        log.info("이니시스 망취소 로직을 진행한다.");
    }

    @Override
    public PaymentType getType() {
        return PaymentType.INICIS;
    }
}
