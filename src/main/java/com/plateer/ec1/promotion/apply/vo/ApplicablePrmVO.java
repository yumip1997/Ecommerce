package com.plateer.ec1.promotion.apply.vo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicablePrmVO implements Comparable<ApplicablePrmVO>{

    private Long cpnIssNo;
    private Long prmNo;
    private String prmNm;
    private String dcCcd;
    private Long dcVal;
    private Long minPurAmt;
    private Long maxDcAmt;
    private String cpnKindCd;
    private String degrCcd;
    private LocalDateTime prmEndDt;

    private String maxBenefitYn = "N";
    private Long bnfVal;

    @Override
    public int compareTo(ApplicablePrmVO o) {
        return Comparator.comparingLong(ApplicablePrmVO::getBnfVal)
                .thenComparing(Comparator.comparingLong(ApplicablePrmVO::getPrmNo).reversed())
                .thenComparing(Comparator.comparingLong(ApplicablePrmVO::getCpnIssNo).reversed())
                .compare(this, o);
    }
}
