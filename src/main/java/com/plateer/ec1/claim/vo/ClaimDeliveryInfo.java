package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
public class ClaimDeliveryInfo implements Cloneable{

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
