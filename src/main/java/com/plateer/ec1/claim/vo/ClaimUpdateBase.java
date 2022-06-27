package com.plateer.ec1.claim.vo;

import com.plateer.ec1.common.model.order.OpOrdBnfInfo;
import com.plateer.ec1.common.model.order.OpClmInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ClaimUpdateBase {

    OpClmInfo orgOpClmInfo;
    OpClmInfo opClmInfo;
    OpOrdBnfInfo orderBenefit;
}
