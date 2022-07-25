package com.plateer.ec1.order.vo;

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

}
