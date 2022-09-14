package com.plateer.ec1.claim.vo;

import com.plateer.ec1.claim.validation.groups.ClaimGroups;
import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.delivery.enums.DVP0002Code;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.order.enums.OPT0006Code;
import com.plateer.ec1.order.enums.OPT0008Code;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDeliveryCostInfo implements Cloneable {

    @NotEmpty
    private String ordCstNo;
    private Long dvGrpNo;
    private String aplyCcd;
    private String orgOrdCstNo;
    private String clmNo;
    private String ordNo;
    private String dvAmtTpCd;
    private Long orgDvAmt;
    private Long dvBnfAmt;
    private Long aplyDvAmt;
    @NotEmpty(groups = {ClaimGroups.GnlRtrnAcpt.class ,ClaimGroups.GnlRtrnWtdwl.class
                        ,ClaimGroups.GnlExchAcpt.class ,ClaimGroups.GnlExchWtdwl.class})
    private String imtnRsnCcd;
    @NotEmpty
    private String dvPlcTpCd;
    private Long cnclDvAmt;

    @Override
    public ClaimDeliveryCostInfo clone() {
        try {
            return (ClaimDeliveryCostInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public OpOrdCostInfo convertOpOrdCostInfo() {
        OpOrdCostInfo opOrdCostInfo = new OpOrdCostInfo();
        BeanUtils.copyProperties(this, opOrdCostInfo);
        return opOrdCostInfo;
    }

    private String reverseAplyCnclCcd(){
        return isApply() ? OPT0005Code.CANCEL.code : OPT0005Code.APPLY.code;
    }

    public boolean isApply(){
        return OPT0005Code.APPLY.code.equals(this.aplyCcd);
    }

    public boolean isCompanyImtnRsnCcd() {
        return OPT0008Code.COMPANY.code.equals(this.getImtnRsnCcd());
    }

    public boolean isCustomerImtnRsnCcd() {
        return OPT0008Code.CUSTOMER.code.equals(this.getImtnRsnCcd());
    }

    public boolean isCustomerDeliveryPaid() {
        return isCustomerImtnRsnCcd() && isDeliveryPaid();
    }

    public boolean isCustomerNotDeliveryPaid() {
        return isCustomerImtnRsnCcd() && !isDeliveryPaid();
    }

    public boolean isDeliveryPaid() {
        return DVP0002Code.PAY_ON_DELIVERY.code.equals(this.getDvPlcTpCd());
    }

    public OpOrdCostInfo toUpdateCnclDvAmtOpOrdCostInfo(){
        return OpOrdCostInfo.builder()
                .ordCstNo(this.ordCstNo)
                .cnclDvAmt(this.aplyDvAmt)
                .build();
    }

    public OpOrdCostInfo toOpOrdCostInfoOfCancel() {
        ClaimDeliveryCostInfo clone = this.clone();
        clone.setAplyCcd(clone.reverseAplyCnclCcd());
        clone.setOrdCstNo(clone.getOrdCstNo());
        return clone.convertOpOrdCostInfo();
    }

    public OpOrdCostInfo toOpOrdCostInfoExchange() {
        return OpOrdCostInfo.builder()
                .ordNo(this.ordNo)
                .dvGrpNo(this.dvGrpNo + 1)
                .dvAmtTpCd(OPT0006Code.EXCHANGE_FEE.code)
                .aplyDvAmt(0)
                .cnclDvAmt(0)
                .orgDvAmt(3000)
                .dvBnfAmt(3000)
                .imtnRsnCcd(this.imtnRsnCcd)
                .dvPlcTpCd(this.dvPlcTpCd)
                .aplyCcd(OPT0005Code.APPLY.code)
                .build();
    }

    public OpOrdCostInfo toOpOrdCostInfoOfReturnCustomerPayOnDelivery() {
        return OpOrdCostInfo.builder()
                .ordNo(this.ordNo)
                .dvGrpNo(this.dvGrpNo + 1)
                .aplyCcd(OPT0005Code.APPLY.code)
                .dvAmtTpCd(OPT0006Code.RETURN_FEE.code)
                .aplyDvAmt(0)
                .cnclDvAmt(0)
                .orgDvAmt(3000)
                .dvBnfAmt(3000)
                .dvPlcTpCd(DVP0002Code.PAY_ON_DELIVERY.code)
                .imtnRsnCcd(OPT0008Code.CUSTOMER.code)
                .build();
    }

    public OpOrdCostInfo toOpOrdCostInfoOfReturnCustomerNotPayOnDelivery() {
        return OpOrdCostInfo.builder()
                .ordNo(this.ordNo)
                .dvGrpNo(this.dvGrpNo + 1)
                .aplyCcd(OPT0005Code.APPLY.code)
                .dvAmtTpCd(OPT0006Code.RETURN_FEE.code)
                .aplyDvAmt(3000)
                .cnclDvAmt(0)
                .orgDvAmt(3000)
                .dvBnfAmt(0)
                .dvPlcTpCd(DVP0002Code.PAY.code)
                .imtnRsnCcd(OPT0008Code.CUSTOMER.code)
                .build();
    }

    public List<OpOrdCostInfo> toOpOrdCostInfoOfReturnCompany() {
        OpOrdCostInfo apply = OpOrdCostInfo.builder()
                .ordNo(this.ordNo)
                .dvGrpNo(this.dvGrpNo + 1)
                .aplyCcd(OPT0005Code.APPLY.code)
                .dvAmtTpCd(OPT0006Code.RETURN_FEE.code)
                .aplyDvAmt(0)
                .cnclDvAmt(0)
                .orgDvAmt(3000)
                .dvBnfAmt(3000)
                .dvPlcTpCd(DVP0002Code.PAY.code)
                .imtnRsnCcd(OPT0008Code.COMPANY.code)
                .build();

        OpOrdCostInfo orgOrdDvCancel = OpOrdCostInfo.builder()
                .ordNo(this.ordNo)
                .dvGrpNo(this.dvGrpNo)
                .aplyCcd(OPT0005Code.CANCEL.code)
                .dvAmtTpCd(OPT0006Code.DV_FEE.code)
                .aplyDvAmt(this.aplyDvAmt)
                .cnclDvAmt(this.cnclDvAmt)
                .orgDvAmt(this.orgDvAmt)
                .dvBnfAmt(this.dvBnfAmt)
                .orgOrdCstNo(this.ordCstNo)
                .dvPlcTpCd(this.dvPlcTpCd)
                .dvPlcTpCd(DVP0002Code.FREE.code)
                .build();

        return Arrays.asList(apply, orgOrdDvCancel);
    }

}
