package com.plateer.ec1.payment.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrderInfoVO {

    //주문번호
    private String ordNo;
    private String goodName;
    private String buyerName;
    private String buyerEmail;

}
