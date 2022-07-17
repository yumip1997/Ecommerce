package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.enums.PaymentType;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayInfoVO {

    @NotNull
    private long payAmount;
    @NotNull
    private String bankCode;
    @NotNull
    private String depositorName;
    @NotNull
    private PaymentType paymentType;
    @NotNull
    private PaymentBusiness paymentBusiness;
}
