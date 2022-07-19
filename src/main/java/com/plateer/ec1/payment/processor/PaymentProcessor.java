package com.plateer.ec1.payment.processor;

import com.plateer.ec1.common.factory.CustomFactory;
import com.plateer.ec1.common.model.order.OpPayInfoModel;
import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.OrderInfoVO;
import com.plateer.ec1.payment.vo.res.ApproveResVO;
import com.plateer.ec1.payment.vo.PayCancelInfoVO;
import com.plateer.ec1.payment.vo.PayInfoVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

public interface PaymentProcessor extends CustomFactory<PaymentType> {

    ApproveResVO approvePay(OrderInfoVO orderInfoVO, PayInfoVO payInfoVO);
    void cancelPay(@Valid PayCancelInfoVO payCancelInfoVO);

    default boolean isPartialCancel(long payAmt, long cnlAmt){
        return payAmt != cnlAmt;
    }

}
