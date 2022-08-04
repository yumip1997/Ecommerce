package com.plateer.ec1.common.model.order;

import com.plateer.ec1.common.model.BaseModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpDvpInfo extends BaseModel {

    private int dvGrpNo;
    private String ordNo;
    private int dvpSeq;
    private String dvMthdCd;

}
