package com.plateer.ec1.order.vo.base;

import com.plateer.ec1.claim.validation.groups.Claim;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBenefitBaseVO implements Cloneable{

    @NotEmpty(groups = Claim.class)
    private String ordNo;
    private String ordBnfNo;
    @NotNull(groups = Claim.class)
    private Long ordSeq;
    @NotNull(groups = Claim.class)
    private Long procSeq;
    @NotNull(groups = Claim.class)
    private Long prmNo;
    @NotNull(groups = Claim.class)
    private Long cpnIssNo;
    private String cpnKndCd;
    private int degrCcd;
    private int aplyAmt;

    public OpOrdBnfInfo toOpOrdBnfInfo(String bnfNo){
        return OpOrdBnfInfo.builder()
                .ordBnfNo(bnfNo)
                .prmNo(this.prmNo)
                .cpnIssNo(this.cpnIssNo)
                .cpnKndCd(this.cpnKndCd)
                .degrCcd(this.degrCcd)
                .ordBnfAmt(this.aplyAmt)
                .ordCnclBnfAmt(0)
                .build();
    }

    public OpOrdBnfRelInfo toOpOrdBnfRelInfo(String ordNo, long ordSeq, String bnfNo){
        return OpOrdBnfRelInfo.builder()
                .ordNo(ordNo)
                .ordBnfNo(bnfNo)
                .ordSeq(ordSeq)
                .procSeq(1)
                .aplyCnclCcd(OPT0005Code.APPLY.getCode())
                .aplyAmt(this.aplyAmt)
                .build();
    }

    public CupIssVO toCupIssVO(String mbrNo){
        return CupIssVO.builder()
                .mbrNo(mbrNo)
                .cpnIssNo(this.cpnIssNo)
                .build();
    }

    public <T> OpOrdBnfRelInfo convertOpOrdBnfRelInfo(){
        OpOrdBnfRelInfo opOrdBnfRelInfo = new OpOrdBnfRelInfo();
        BeanUtils.copyProperties(this, opOrdBnfRelInfo);
        return opOrdBnfRelInfo;
    }

    @Override
    public OrderBenefitBaseVO clone() {
        try {
            return (OrderBenefitBaseVO) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
