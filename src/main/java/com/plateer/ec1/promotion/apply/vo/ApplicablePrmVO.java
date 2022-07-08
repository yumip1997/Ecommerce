package com.plateer.ec1.promotion.apply.vo;

import com.plateer.ec1.common.enums.CommonConstants;
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

    @Builder.Default
    private String maxBenefitYn = CommonConstants.N.getCode();
    @Builder.Default
    private String applyingYn = CommonConstants.N.getCode();
    private Long bnfVal;

    @Override
    public int compareTo(ApplicablePrmVO o) {
        return Comparator.comparing(ApplicablePrmVO::getBnfVal)
                .thenComparing(ApplicablePrmVO::getApplyingYn)
                .thenComparing(ApplicablePrmVO::getPrmNo).reversed()
                .thenComparing(ApplicablePrmVO::getCpnIssNo).reversed()
                .compare(this, o);
    }
}
