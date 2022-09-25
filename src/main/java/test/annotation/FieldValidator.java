package test.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldValidator implements ConstraintValidator<NotNull, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? false : ("null".equalsIgnoreCase(value) ? false : value.isEmpty() ? false : true);
    }
}
