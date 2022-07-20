package com.plateer.ec1.payment.vo.req;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
