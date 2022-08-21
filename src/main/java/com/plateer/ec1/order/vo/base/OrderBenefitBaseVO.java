package com.plateer.ec1.order.vo.base;

import com.plateer.ec1.claim.enums.define.OpBnfBase;
import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import com.plateer.ec1.promotion.cupusecnl.vo.reqeust.CupIssVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBenefitBaseVO {

    private String ordNo;
    private String bnfNo;
    private long ordSeq;
    private long procSeq;
    private Long prmNo;
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

    public OpOrdBnfRelInfo toInsertOpOrdBnfRelInfo(OpBnfBase opBnfBase, String clmNo){
        return opBnfBase.getOpOrdBnfRelInfo(this, clmNo);
    }

    public OpOrdBnfInfo toUpdateOpOrdBnfInfo(OpBnfBase opBnfBase){
        return opBnfBase.getOpOrdBnfInfo(this);
    }

    public CupIssVO toCupIssVO(String mbrNo){
        return CupIssVO.builder()
                .mbrNo(mbrNo)
                .cpnIssNo(this.cpnIssNo)
                .build();
    }

}
