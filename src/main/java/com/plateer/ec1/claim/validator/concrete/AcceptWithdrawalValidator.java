package com.plateer.ec1.claim.validator.concrete;

import com.plateer.ec1.claim.enums.ValidatorType;
import com.plateer.ec1.claim.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimBaseVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AcceptWithdrawalValidator extends ClaimValidator {

    @Override
    public void isValid(ClaimBaseVO claimBaseVO) throws Exception {
        log.info("파라미터 유효성 검증 로직을 진행한다.");

        super.isValidOrdPrgsScd(claimBaseVO);
        super.isValidProductType(claimBaseVO);
    }

    @Override
    public void verifyAmount(ClaimBaseVO claimBaseVO) throws Exception {
        log.info("금액검증 로직을 진행한다.");
        //UI 상의 취소 금액 = 주문금액 - 혜택금액 -배송비 = 취소금액
    }

    @Override
    public ValidatorType getType() {
        return ValidatorType.ACCEPT_WITHDRAWAL;
    }
}
