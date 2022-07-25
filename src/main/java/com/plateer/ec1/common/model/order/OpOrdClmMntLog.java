package com.plateer.ec1.common.model.order;

import com.plateer.ec1.common.model.BaseModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OpOrdClmMntLog extends BaseModel {

    private Long logSeq;
    private String ordNo;
    private String clmNo;
    private String reqPram;
    private String insData;
    private String uptData;
    private String procCcd;

}
