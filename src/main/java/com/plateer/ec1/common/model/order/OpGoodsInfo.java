package com.plateer.ec1.common.model.order;

import java.time.LocalDateTime;

public class OpGoodsInfo {

    private String ordNo;
    //상품번호
    private String ordGoodsNo;
    //단품번호
    private String ordItemNo;
    //상품판매유형코드
    private String goodsSellTpCd;
    //상품배송유형코드
    private String goodsDlvTpCd;
    private String goodsNm;
    private String itemNm;
    private Long sellAmt;
    private Long sellDcAmt;
    private LocalDateTime sysRegDtime;
    private String sysRegrId;
    private LocalDateTime sysModDtime;
    private String sysModrId;

}
