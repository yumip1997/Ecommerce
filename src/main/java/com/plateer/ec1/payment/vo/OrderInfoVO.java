package com.plateer.ec1.payment.vo;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderInfoVO {

    @NotNull
    private String ordNo;
    private String goodName;
    private String buyerName;
    private String buyerEmail;

}
