package com.plateer.ec1.order.vo.base;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderClaimBaseVO {

    @NotEmpty
    private String ordNo;
    private String clmNo;

}
