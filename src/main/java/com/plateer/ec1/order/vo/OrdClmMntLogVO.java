package com.plateer.ec1.order.vo;

import com.google.gson.JsonObject;
import com.plateer.ec1.common.model.order.OpOrdClmMntLog;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrdClmMntLogVO {

    private Long logSeq;
    private String ordNo;
    private String clmNo;
    private JsonObject insData;
    private JsonObject uptData;
    private String procCcd;

    public OpOrdClmMntLog toModel(){
        return OpOrdClmMntLog.builder()
                .logSeq(this.getLogSeq())
                .ordNo(this.getOrdNo())
                .clmNo(this.getClmNo())
                .insData(this.getInsData().toString())
                .uptData(this.getUptData().toString())
                .build();
    }

}
