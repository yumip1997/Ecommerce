package com.plateer.ec1.promotion.apply.vo;

import com.plateer.ec1.common.enums.CommonConstants;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.function.ToLongFunction;

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

    private String maxBenefitYn = CommonConstants.N.getCode();
    private String applyingYn = CommonConstants.N.getCode();
    private Long bnfVal;

    @Override
    public int compareTo(ApplicablePrmVO o) {
        return Comparator.comparingLong(ApplicablePrmVO::getBnfVal)
                .thenComparing(ApplicablePrmVO::getApplyingYn)
                .thenComparing(ApplicablePrmVO::getPrmNo).reversed()
                .thenComparing(ApplicablePrmVO::getCpnIssNo).reversed()
                .compare(this, o);
    }
}
