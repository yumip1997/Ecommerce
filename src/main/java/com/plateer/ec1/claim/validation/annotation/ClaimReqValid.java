package com.plateer.ec1.claim.validation.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ClaimReqValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClaimReqValid {

    String message() default "Claim Request is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
