package com.plateer.ec1.promotion.download.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class CupDwlVO {

    long prmNo;
    String prmNm;
    String useYn;
    //TODO 확인하기
    Timestamp dwlEndDd;
    long dwlPsbCnt;
    long psnDwlCnt;
    long totalCnt;
    long mbrCnt;

}
