package com.plateer.ec1.order.vo.base;

import com.plateer.ec1.claim.validation.groups.ClaimGroups;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderClaimBaseVO {

    @NotEmpty
    private String ordNo;
    @NotEmpty(groups = ClaimGroups.GnlOrdCnl.class)
    private String clmNo;

}
