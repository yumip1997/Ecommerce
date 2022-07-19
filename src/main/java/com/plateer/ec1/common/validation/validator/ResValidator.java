package com.plateer.ec1.common.validation.validator;

import com.plateer.ec1.common.validation.annotation.ResValid;
import com.plateer.ec1.common.vo.ValidResVO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ResValidator implements ConstraintValidator<ResValid, ValidResVO> {

    @Override
    public void initialize(ResValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ValidResVO value, ConstraintValidatorContext context) {
        return value.isValidRes();
    }

}
