package com.plateer.ec1.claim.vo;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDeliveryInfo implements Cloneable{

    @NotEmpty
    private String ordCstNo;
    @NotNull
    private Long dvGrpNo;
    @NotEmpty
    private String aplyCcd;
    private String orgOrdCstNo;
    private String clmNo;
    @NotEmpty
    private String ordNo;
    @NotEmpty
    private String dvAmtTpCd;
    @NotNull
    private Long orgDvAmt;
    @NotNull
    private Long dvBnfAmt;
    @NotNull
    private Long aplyDvAmt;
    private String imtnRsnCcd;
    @NotEmpty
    private String dvPlcTpCd;
    @NotNull
    private Long cnclDvAmt;

    @Override
    public ClaimDeliveryInfo clone() {
        try {
            return (ClaimDeliveryInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
