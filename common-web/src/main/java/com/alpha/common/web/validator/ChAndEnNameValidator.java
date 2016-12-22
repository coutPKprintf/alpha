package com.alpha.common.web.validator;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class ChAndEnNameValidator implements ConstraintValidator<ChAndEnName, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(ChAndEnName name) {
        allowEmpty = name.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FA5·a-zA-Z0-9-_]{1,45}");
        return (allowEmpty && StringUtils.isEmpty(s)) || pattern.matcher(s).find();
    }
}
