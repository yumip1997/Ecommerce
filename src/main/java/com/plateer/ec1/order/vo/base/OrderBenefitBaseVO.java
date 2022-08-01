package com.plateer.ec1.order.vo.base;

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
    private String degrCcd;
    private long aplyAmt;

}
