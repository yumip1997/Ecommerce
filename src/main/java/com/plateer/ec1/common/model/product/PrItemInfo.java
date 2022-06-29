package com.plateer.ec1.common.model.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PrItemInfo {

    private String goodsNo;
    private String itemNo;
    private String itemNm;
    private LocalDateTime sysRegDtime;
    private LocalDateTime sysModDtime;
    private String sysRegrId;
    private String sysModrId;

}
