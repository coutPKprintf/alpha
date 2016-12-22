package com.alpha.common.web.validator;


import com.alpha.common.utils.IdcardUtils;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 16-1-15.
 */
public class IDCardValidator implements ConstraintValidator<IDCard, String> {
    boolean allowEmpty = false;

    @Override
    public void initialize(IDCard idCard) {
        allowEmpty = idCard.allowEmpty();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (allowEmpty && StringUtils.isEmpty(s)) || (StringUtils.isNotEmpty(s) && IdcardUtils.validateCard(s));
    }
}
