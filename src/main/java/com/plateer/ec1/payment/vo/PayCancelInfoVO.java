package com.plateer.ec1.payment.vo;

import com.plateer.ec1.common.validation.annotation.PayCancel;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PayCancel
public class PayCancelInfoVO {

    @NotEmpty
    private String ordNo;
    @NotEmpty
    private String clmNo;
    @NotNull
    private long cnclAmt;
}
