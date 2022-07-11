package com.plateer.ec1.payment.vo.req;

import com.plateer.ec1.payment.enums.PaymentType;
import com.plateer.ec1.payment.vo.OriginOrderVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancelReqVO {
    PaymentType paymentType;
    OriginOrderVO originOrderVO;
}
