package com.plateer.ec1.payment.processor;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.OrderPayInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;

import javax.validation.Valid;

public interface PaymentProcessor extends CustomFactory<PaymentType> {

    ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO);
    void cancelPay(@Valid OrderPayInfoVO orderPayInfoVO);

    default boolean isPartialCancel(long payAmt, long cnlAmt){
        return payAmt != cnlAmt;
    }

}
