package com.plateer.ec1.payment.vo;

import com.plateer.ec1.common.validation.annotation.PayCancel;
import com.plateer.ec1.payment.enums.PaymentType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PayCancel
public class OrderPayInfoVO {

    private long cnclReqAmt;
    private String payNo;
    private String ordNo;
    private String clmNo;
    private String payMnCd;
    private String payCcd;
    private String payPrgsScd;
    private long payAmt;
    private long cnclAmt;
    private long rfndAvlAmt;
    private String trsnId;
    private String orgPayNo;
    private String vrAcct;
    private String vrAcctNm;
    private String vrBnkCd;
    private String vrValDt;
    private String vrValTt;
    private String rfndBnkCk;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;
    private String goodsNm;
    private String mbrNm;

    public boolean isPartialCancel(){
        return this.getPayAmt() != this.getCnclReqAmt();
    }

    public OrderInfoVO toOrderInfoVO(){
        return OrderInfoVO.builder()
                .ordNo(this.getOrdNo())
                .clmNo(this.getClmNo())
                .goodName(this.getGoodsNm())
                .buyerName(this.getMbrNm())
                .build();
    }

    public PayInfoVO toPayInfoVO(){
        return PayInfoVO.builder()
                .payAmount(this.getPayAmt() - this.getCnclReqAmt())
                .bankCode(this.getRfndBnkCk())
                .depositorName(this.getRfndAcctOwnNm())
                .paymentType(PaymentType.INICIS)
                .build();
    }
}
