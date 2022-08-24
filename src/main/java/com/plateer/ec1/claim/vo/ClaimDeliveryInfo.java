package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import lombok.*;
import org.springframework.beans.BeanUtils;

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

    public boolean isApply() {
        return this.aplyCcd.equals(OPT0005Code.APPLY.code);
    }

    public OpOrdCostInfo toOpOrdCostInfo(String clmNo) {
        boolean isApply = this.isApply();
        return OpOrdCostInfo.builder()
                .dvGrpNo(isApply ? this.dvGrpNo + 1 : this.dvGrpNo)
                .aplyCcd(this.aplyCcd)
                .orgOrdCstNo(isApply ? null : this.ordCstNo)
                .clmNo(clmNo)
                .ordNo(this.ordNo)
                .dvAmtTpCd(this.dvAmtTpCd)
                .orgDvAmt(this.orgDvAmt)
                .dvBnfAmt(this.dvBnfAmt)
                .imtnRsnCcd(this.imtnRsnCcd)
                .dvPlcTpCd(this.dvPlcTpCd)
                .cnclDvAmt(this.cnclDvAmt)
                .build();
    }

    public OpOrdCostInfo toUpdateOpOrdCostInfo(){
        ClaimDeliveryInfo clone = this.clone();
        clone.setCnclDvAmt(this.getAplyDvAmt());

        OpOrdCostInfo opOrdCostInfo = new OpOrdCostInfo();
        BeanUtils.copyProperties(clone, opOrdCostInfo);
        return opOrdCostInfo;
    }

    @Override
    public ClaimDeliveryInfo clone() {
        try {
            return (ClaimDeliveryInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
