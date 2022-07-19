package com.plateer.ec1.payment.vo;

import com.plateer.ec1.common.validation.annotation.PayCancel;
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

}
