package com.plateer.ec1.order.vo;

import com.plateer.ec1.common.model.order.OpOrdClmMntLog;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OrdClmMntLogVO<T, U> {

    private Long logSeq;
    private String ordNo;
    private String clmNo;
    private T insData;
    private U uptData;
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
