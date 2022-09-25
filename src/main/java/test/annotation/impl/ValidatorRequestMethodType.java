package test.annotation.impl;

import test.annotation.RequestTypeAnnotation;
import test.constant.RequestConstant;
import test.dto.RequestDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Locale;
import java.util.Objects;

public class ValidatorRequestMethodType implements ConstraintValidator<RequestTypeAnnotation, RequestDto> {

    @Override
    public boolean isValid(RequestDto requestDto, ConstraintValidatorContext constraintValidatorContext) {
        String methodType = requestDto.getMethodType().toLowerCase(Locale.ROOT);

        if ((methodType.equals(RequestConstant.REQUEST_METHOD[0].toLowerCase(Locale.ROOT))
                || methodType.equals(RequestConstant.REQUEST_METHOD[1].toLowerCase(Locale.ROOT)))
                && !Objects.nonNull(requestDto.getRequestBody())) {
            return true;
        }
        return false;
    }
}
