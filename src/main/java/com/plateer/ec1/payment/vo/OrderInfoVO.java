package com.plateer.ec1.payment.vo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoVO {

    //주문번호
    private String ordNo;
    private String goodName;
    private String buyerName;
    private String buyerEmail;

}
