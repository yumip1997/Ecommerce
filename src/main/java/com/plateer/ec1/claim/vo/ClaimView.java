package com.plateer.ec1.claim.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimView {

    private String ordNo;
    private String clmNo;
    private String ordGoodsNo;
    private String ordItemNo;
    private int ordSeq;
    private int procSeq;
    private String ordClmTpCd;
    private String dvRvtCcd;
    private String ordPrgsScd;
    private String goodsSellTpCd;

}
