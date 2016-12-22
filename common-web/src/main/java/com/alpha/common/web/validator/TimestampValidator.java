package com.alpha.common.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 15-11-30.
 */
public class TimestampValidator implements ConstraintValidator<Timestamp, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null || String.valueOf(value.longValue()).length() == 13){
            return true;
        }
        return false;
    }

    @Override
    public void initialize(Timestamp constraintAnnotation) {
    }

}