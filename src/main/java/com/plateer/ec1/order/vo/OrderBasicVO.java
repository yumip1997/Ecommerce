package com.plateer.ec1.order.vo;

import com.plateer.ec1.order.validation.groups.GeneralPrd;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBasicVO {

    @NotNull
    private String mbrNo;
    @NotNull
    private String ordTpCd;
    @NotNull
    private String ordSysCcd;
    @NotNull
    private String ordNm;
    @NotNull
    private String ordSellNo;
    @NotNull
    private String ordAddr;
    @NotNull
    private String ordAddrDtl;
    private String rfndBnkCk;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;

}
