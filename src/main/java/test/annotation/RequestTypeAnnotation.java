package test.annotation;

import test.annotation.impl.ValidatorRequestMethodType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidatorRequestMethodType.class)
public @interface RequestTypeAnnotation {

     String message() default "Request body can't be null for Post request ";

     Class<?>[] groups() default {};

     Class<? extends Payload>[] payload() default {};
}
