package com.plateer.ec1.promotion.apply.vo;

import lombok.*;

import java.util.Comparator;

@Getter
@Setter
@Builder
public class ApplicablePrmVO implements Comparable<ApplicablePrmVO>{

    private Long prmNo;
    private String prmNm;
    private String dcCcd;
    private Long dcVal;
    private Long bnfVal;
    private Long minPurAmt;
    private Long maxDcAmt;
    private Long cpnIssNo;
    private String maxBenefitYn;

    @Override
    public int compareTo(ApplicablePrmVO o) {
        return Comparator.comparingLong(ApplicablePrmVO::getBnfVal)
                .thenComparing(Comparator.comparingLong(ApplicablePrmVO::getPrmNo).reversed())
                .thenComparing(Comparator.comparingLong(ApplicablePrmVO::getCpnIssNo).reversed())
                .compare(this, o);
    }
}
