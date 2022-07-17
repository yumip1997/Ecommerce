package com.plateer.ec1.payment.vo.inicis.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VacctCnlReqVO {

    private String type;
    private String paymethod;
    private String timestamp;
    private String clientIp;
    private String mid;
    private String tid;
    private String msg;
    private String refundAcctNum;
    private String refundBankCode;
    private String refundAcctName;
    private String hashData;


}
