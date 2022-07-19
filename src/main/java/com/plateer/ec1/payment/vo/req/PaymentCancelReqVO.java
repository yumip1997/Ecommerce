package com.plateer.ec1.payment.vo.req;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class PaymentCancelReqVO {

    @NotNull
    PaymentType paymentType;
    @NotNull
    private String ordNo;
    @NotNull
    private String clmNo;
    @NotNull
    private long cnclReqAmt;
}
