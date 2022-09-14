package com.plateer.ec1.claim.validation.annotation;

import com.plateer.ec1.claim.enums.ClaimBusiness;
import com.plateer.ec1.claim.vo.ClaimRequestVO;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@RequiredArgsConstructor
public class ClaimReqValidator implements ConstraintValidator<ClaimReqValid, ClaimRequestVO> {

    private final Validator validator;

    @Override
    public boolean isValid(ClaimRequestVO claimRequestVO, ConstraintValidatorContext context) {
        ClaimBusiness claimBusiness = ClaimBusiness.of(claimRequestVO);
        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(claimRequestVO, claimBusiness.getValidationGroup());

        if (!CollectionUtils.isEmpty(constraintViolations)){
            addConstraintViolations(context, constraintViolations);
            return false;
        }

        return true;
    }

    private void addConstraintViolations(ConstraintValidatorContext context, Set<ConstraintViolation<Object>> constraintViolations){
        context.disableDefaultConstraintViolation();

        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            context.buildConstraintViolationWithTemplate(constraintViolation.getMessageTemplate())
                    .addPropertyNode(constraintViolation.getPropertyPath().toString())
                    .addConstraintViolation();
        }
    }
}
