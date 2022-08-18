package com.plateer.ec1.payment.vo;

import com.plateer.ec1.payment.enums.PaymentBusiness;
import com.plateer.ec1.promotion.enums.PRM0011Code;
import com.plateer.ec1.promotion.point.vo.PointVO;
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
    private String mbrNo;
    private String clmNo;
    private String goodName;
    private String buyerName;
    private String buyerEmail;

    private PaymentBusiness paymentBusiness;

    public PointVO toUsePointVO(String payNo, Long usePntAmt){
        return PointVO.builder()
                .mbrNo(this.mbrNo)
                .ordNo(this.ordNo)
                .svUseAmt(usePntAmt)
                .svUseCcd(PRM0011Code.USE.getCode())
                .payNo(payNo)
                .build();
    }

}
