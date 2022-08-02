package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpOrdBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBasicVO {

    @NotEmpty
    private String mbrNo;
    @NotEmpty
    private String ordTpCd;
    @NotEmpty
    private String ordSysCcd;
    @NotEmpty
    private String ordNm;
    @NotEmpty
    private String ordSellNo;
    @NotEmpty
    private String ordAddr;
    @NotEmpty
    private String ordAddrDtl;
    private String rfndBnkCk;
    private String rfndAcctNo;
    private String rfndAcctOwnNm;

    public OpOrdBase toOpOrdBase(String ordNo){
        return OpOrdBase.builder()
                .ordNo(ordNo)
                .mbrNo(this.getMbrNo())
                .ordTpCd(this.getOrdTpCd())
                .ordSysCcd(this.getOrdSysCcd())
                .ordNm(this.getOrdNm())
                .ordSellNo(this.getOrdSellNo())
                .ordAddr(this.getOrdAddr())
                .ordAddrDtl(this.getOrdAddrDtl())
                .rfndBnkCk(this.getRfndBnkCk())
                .rfndAcctNo(this.getRfndAcctNo())
                .rfndAcctOwnNm(this.getRfndAcctOwnNm())
                .build();
    }

}
