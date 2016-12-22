package com.alpha.common.web.validator;


import com.alpha.common.utils.FieldUtil;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class UserNameValidator implements ConstraintValidator<UserName, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(UserName name) {
        allowEmpty = name.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && FieldUtil.isUsername(s));
    }
}
