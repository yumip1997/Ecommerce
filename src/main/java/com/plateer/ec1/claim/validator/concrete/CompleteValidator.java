package com.plateer.ec1.claim.validator.concrete;

import com.plateer.ec1.claim.enums.ClaimValidatorType;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class CompleteValidator extends ClaimValidator {

    @Override
    public void isValid(ClaimVO claimVO) throws Exception {
        log.info("파라미터 유효성 검증 로직을 진행한다.");

        super.isValidOrdPrgsScd(claimVO);
        super.isValidProductType(claimVO);
    }

    @Override
    public void verifyAmount(ClaimVO claimVO) throws Exception {
        //주문결제테이블의 결제금액 - 취소금액 = 환불가능금액 인지 판단
        log.info("금액검증 로직을 진행한다.");
    }

    @Override
    public ClaimValidatorType getType() {
        return ClaimValidatorType.COMPLETE;
    }
}
