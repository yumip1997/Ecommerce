package com.plateer.ec1.common.validation.annotation;

import com.plateer.ec1.common.validation.validator.ResValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ResValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ResValid {

    String message() default "성공 응답 코드가 아닙니다!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
