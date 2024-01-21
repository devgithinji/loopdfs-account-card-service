package org.loopdfs.accountcardservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.loopdfs.accountcardservice.model.CardType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CardTypeValidator implements ConstraintValidator<ValidCardType, String> {

    private String message;

    @Override
    public void initialize(ValidCardType constraintAnnotation) {
        this.message = constraintAnnotation.message();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        List<String> cardTypes = Arrays.stream(CardType.values()).map(Enum::toString).toList();

        boolean isValid = Optional.ofNullable(value)
                .map(s -> cardTypes.contains(s.toUpperCase()))
                .orElse(false);

        if (!isValid) {
            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return isValid;
    }
}
