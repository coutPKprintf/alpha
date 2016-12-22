package com.alpha.common.web.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(Password password) {
        allowEmpty = password.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("[0-9a-zA-Z\\.@-_]{6,20}");
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && pattern.matcher(s).find());
    }
}
