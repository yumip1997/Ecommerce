package com.plateer.ec1.common.model.order;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpDvpInfo {

    private int dvGrpNo;
    private String ordNo;
    private int dvpSeq;
    private String dvMthdCd;

}
