package com.plateer.ec1.promotion.cupusecnl.vo.reqeust;

import com.plateer.ec1.promotion.com.validation.CupRestore;
import com.plateer.ec1.promotion.com.validation.CupUse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CupIssVO {

    @NotNull(groups = {CupUse.class, CupRestore.class})
    private Long cpnIssNo;
    @NotNull(groups = {CupUse.class})
    private String ordNo;
    @NotNull(groups = {CupUse.class, CupRestore.class})
    private String mbrNo;


}
