package test.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FieldValidator.class)
@Documented
public @interface NotNull {
    public String message() default "Value can't be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
