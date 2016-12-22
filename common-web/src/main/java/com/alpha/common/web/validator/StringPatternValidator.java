package com.alpha.common.web.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class StringPatternValidator implements ConstraintValidator<StringPattern, String> {
    String regex = null;

    @Override
    public void initialize(StringPattern stringPattern) {
        regex = stringPattern.regexp();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(s)) {
            return true;
        }
        return Pattern.compile(regex).matcher(s).find();
    }
}
