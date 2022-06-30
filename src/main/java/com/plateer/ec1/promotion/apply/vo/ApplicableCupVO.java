package com.plateer.ec1.promotion.apply.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicableCupVO {

    private Long prmNo;
    private String prmNm;
    private String dcCcd;
    private Long dcVal;
    private Long bnfVal;    //혜택금액
    private Long minPurAmt;
    private Long maxDcAmt;
    private Long cpnIssNo;
    private String maxBenefitYn;


}
