package org.loopdfs.accountcardservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardTypeValidator.class)
public @interface ValidCardType {
    String message() default "Invalid card type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
