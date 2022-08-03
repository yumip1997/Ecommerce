package com.plateer.ec1.order.vo.base;

import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpOrdBnfRelInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBenefitBaseVO {

    private Long prmNo;
    private Long cpnIssNo;
    private String cpnKndCd;
    private int degrCcd;
    private int aplyAmt;

    public OpOrdBnfInfo toOpOrdBnfInfo(String bnfNo){
        return OpOrdBnfInfo.builder()
                .ordBnfNo(bnfNo)
                .prmNo(this.cpnIssNo)
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
                .aplyCnclCcd("")
                .aplyAmt(this.aplyAmt)
                .build();
    }

}
