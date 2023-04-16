package com.plateer.ec1.claim.validation.validator.impl;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.validation.validator.ClaimValidator;
import com.plateer.ec1.claim.vo.ClaimValidationVO;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Order(2)
public class OrderCancelValidator implements ClaimValidator {

    @Override
    public void validate(ClaimValidationVO validationVO) {
        // if/else 분기
        // 일반상품 주문취소 validation - 최소구매수량
        // 모바일쿠폰 주문취소 validation - 최소구매수량, 사용여부
    }

    @Override
    public List<ClaimBusiness> getTypes() {
        return Arrays.asList(ClaimBusiness.GCC, ClaimBusiness.MCA);
    }
}
