package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NetCancelReq {

    PaymentType paymentType;
}
