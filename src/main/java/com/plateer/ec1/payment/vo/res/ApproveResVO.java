package com.plateer.ec1.payment.vo.res;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApproveResVO {

    private PaymentType paymentType;
    private String ablePartialCancelYn;

}
