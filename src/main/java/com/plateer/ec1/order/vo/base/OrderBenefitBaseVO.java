package com.plateer.ec1.order.vo.base;

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
    private int ordBnfAmt;

}
