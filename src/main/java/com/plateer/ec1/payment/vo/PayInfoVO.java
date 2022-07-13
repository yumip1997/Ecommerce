package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.payment.enums.PaymentType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayInfoVO {

    private long payAmount;
    private String bankCode;
    private String depositorName;
    private PaymentType paymentType;
    private PaymentBusiness paymentBusiness;
}
