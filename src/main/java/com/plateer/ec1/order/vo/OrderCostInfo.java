package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpOrdCostInfo;
import com.plateer.ec1.order.enums.OPT0005Code;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCostInfo {

    @NotEmpty
    private String dvAmtTpCd;
    @NotNull
    private Long orgDvAmt;
    @NotNull
    private Long dvBnfAmt;

    public OpOrdCostInfo toOpOrdCostInfo(String ordNo, int dvGrpNo){
        return OpOrdCostInfo.builder()
                .ordNo(ordNo)
                .dvGrpNo(dvGrpNo)
                .aplyCcd(OPT0005Code.APPLY.getCode())
                .dvAmtTpCd(this.dvAmtTpCd)
                .orgDvAmt(this.orgDvAmt)
                .dvBnfAmt(this.dvBnfAmt)
                .aplyDvAmt(this.calculateAplyDvAmt())
                .build();
    }

    private long calculateAplyDvAmt(){
        return this.orgDvAmt - this.dvBnfAmt;
    }

}
