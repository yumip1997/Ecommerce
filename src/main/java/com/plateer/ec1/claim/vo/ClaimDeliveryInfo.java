package com.plateer.ec1.claim.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ClaimDeliveryInfo {

    private String ordCstNo;
    private long dvGrpNo;
    private String aplyCcd;
    private String orgOrdCstNo;
    private String clmNo;
    private String ordNo;
    private String dvAmtTpCd;
    private long orgDvAmt;
    private long dvBnfAmt;
    private long aplyDvAmt;
    private String imtnRsnCcd;
    private String dvPlcTpCd;
    private long cnclDvAmt;

}
