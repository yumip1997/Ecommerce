package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpClmInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.delivery.enums.DVP0002Code;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.order.enums.OPT0006Code;
import com.plateer.ec1.order.enums.OPT0008Code;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.util.MultiValueMap;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClaimDeliveryInfo implements Cloneable{

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
    private String imtnRsnCcd;
    private String dvPlcTpCd;
    private Long cnclDvAmt;
    @NotEmpty
    private String reqDvPlcTpCd;
    @NotEmpty
    private String reqImtnRsnCcd;

    @Override
    public ClaimDeliveryInfo clone() {
        try {
            return (ClaimDeliveryInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public OpOrdCostInfo convertOpOrdCostInfo(){
        OpOrdCostInfo opOrdCostInfo = new OpOrdCostInfo();
        BeanUtils.copyProperties(this, opOrdCostInfo);
        return opOrdCostInfo;
    }

    public int getDvGrpNoOfCreatedClm(Map<String, List<ClaimGoodsInfo>> mapByOrdNoDvGrpNo, Map<String, List<OpClmInfo>> mapByOrdNoOrdSeqOrgProcSeq){
        List<OpClmInfo> createdClmByDvGrp = getCreatedClmByDvGrp(mapByOrdNoDvGrpNo, mapByOrdNoOrdSeqOrgProcSeq);
        return createdClmByDvGrp.get(0).getDvGrpNo();
    }

    private List<OpClmInfo> getCreatedClmByDvGrp(Map<String, List<ClaimGoodsInfo>> mapByOrdNoDvGrpNo, Map<String, List<OpClmInfo>> mapByOrdNoOrdSeqOrgProcSeq){
        List<ClaimGoodsInfo> claimGoodsInfos = mapByOrdNoDvGrpNo.get(this.getOrdNo() + this.getDvGrpNo());
        ClaimGoodsInfo claimGoodsInfo = claimGoodsInfos.get(0);
        return mapByOrdNoOrdSeqOrgProcSeq.get(claimGoodsInfo.getOrdNoOrdSeqProcSeq());
    }

    public boolean isCompanyImtnRsnCcd(){
        return OPT0008Code.COMPANY.code.equals(this.getReqImtnRsnCcd());
    }

    public boolean isCustomerImtnRsnCcd(){
        return OPT0008Code.CUSTOMER.code.equals(this.getReqImtnRsnCcd());
    }

    public boolean isCustomerDeliveryPaid(){
        return isCustomerImtnRsnCcd() && isDeliveryPaid();
     }

    public boolean isCustomerNotDeliveryPaid(){
        return isCustomerImtnRsnCcd() && !isDeliveryPaid();
    }

    public boolean isDeliveryPaid(){
        return DVP0002Code.PAY_ON_DELIVERY.code.equals(this.getReqDvPlcTpCd());
    }

    public OpOrdCostInfo toOpOrdCostInfoOfCancel(){
        ClaimDeliveryInfo clone = this.clone();
        clone.setAplyCcd(OPT0005Code.CANCEL.code);
        clone.setOrdCstNo(clone.getOrdCstNo());
        return clone.convertOpOrdCostInfo();
    }

    public OpOrdCostInfo toOpOrdCostInfoExchange(int dvGrpNo){
        return OpOrdCostInfo.builder()
                .ordNo(this.getOrdNo())
                .dvGrpNo(dvGrpNo)
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

    public OpOrdCostInfo toOpOrdCostInfoOfReturnCustomerPayOnDelivery(int dvGrpNo){
        return OpOrdCostInfo.builder()
                .ordNo(this.ordNo)
                .dvGrpNo(dvGrpNo)
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

    public OpOrdCostInfo toOpOrdCostInfoOfReturnCustomerNotPayOnDelivery(int dvGrpNo){
        return OpOrdCostInfo.builder()
                .ordNo(this.ordNo)
                .dvGrpNo(dvGrpNo)
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

    public List<OpOrdCostInfo> toOpOrdCostInfoOfReturnCompany(int dvGrpNo){
        OpOrdCostInfo apply = OpOrdCostInfo.builder()
                .ordNo(this.ordNo)
                .dvGrpNo(dvGrpNo)
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
                .imtnRsnCcd(this.imtnRsnCcd)
                .dvPlcTpCd(DVP0002Code.FREE.code)
                .imtnRsnCcd(OPT0008Code.COMPANY.code)
                .build();

        return Arrays.asList(apply, orgOrdDvCancel);
    }

}
