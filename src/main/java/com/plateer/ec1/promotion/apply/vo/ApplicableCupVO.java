package com.plateer.ec1.promotion.apply.vo;

import lombok.*;

import java.util.Comparator;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicableCupVO implements Comparable<ApplicableCupVO>{

    private Long prmNo;
    private String prmNm;
    private String dcCcd;
    private Long dcVal;
    private Long bnfVal;    //혜택금액
    private Long minPurAmt;
    private Long maxDcAmt;
    private Long cpnIssNo;
    private String maxBenefitYn;

    @Override
    public int compareTo(ApplicableCupVO o) {
        return Comparator.comparingLong(ApplicableCupVO::getBnfVal)
                .thenComparingLong(ApplicableCupVO::getPrmNo).reversed()
                .thenComparingLong(ApplicableCupVO::getCpnIssNo).reversed()
                .compare(this, o);
    }
}
