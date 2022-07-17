package com.plateer.ec1.payment.processor;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.req.NetCancelReqVO;
import com.plateer.ec1.payment.vo.OriginOrderVO;
import com.plateer.ec1.payment.vo.PayInfoVO;

public interface PaymentProcessor extends CustomFactory<PaymentType> {

    ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO);
    void cancelPay(OriginOrderVO originOrderVO);

    default void verifyAmount(){
        //취소금액 < 결제금액
        //결제완료 시 -> 취소금액 < 환불가능금액
    }

    default boolean isPartialCancel(long payAmt, long cnlAmt){
        return payAmt != cnlAmt;
    }

}
