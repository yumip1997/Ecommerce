package com.plateer.ec1.payment.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Long payAmount;
    private String bankCode;
    private String depositorName;
    @NotNull
    private PaymentType paymentType;
}
