package com.plateer.ec1.promotion.download.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CupInfoVO {

    Long prmNo;
    Long cpnIssNo;
    String prmNm;
    String useYn;
    //TODO 확인하기
    Timestamp dwlEndDd;
    Long dwlPsbCnt;
    Long psnDwlPsbCnt;
    Long totalCnt;
    Long mbrCnt;

}
