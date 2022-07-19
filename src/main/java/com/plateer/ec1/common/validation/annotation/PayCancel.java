package com.plateer.ec1.common.validation.annotation;

import com.plateer.ec1.common.validation.validator.PayCancelValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PayCancelValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PayCancel {

    String message() default "Cancel is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
