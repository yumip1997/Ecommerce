package com.plateer.ec1.payment.vo.res;

import com.plateer.ec1.payment.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApproveResVO {

    private PaymentType paymentType;
    private String ablePartialCancelYn;

}
