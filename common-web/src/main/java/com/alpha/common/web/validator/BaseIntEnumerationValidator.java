package com.alpha.common.web.validator;


import com.alpha.common.enums.BaseIntEnum;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 15-11-30.
 */
public class BaseIntEnumerationValidator implements ConstraintValidator<BaseIntEnumeration, Integer> {

    List<Integer> valueList = null;

    boolean isAllowEmpty;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value == null ? isAllowEmpty : !valueList.contains(value);
    }

    @Override
    public void initialize(BaseIntEnumeration constraintAnnotation) {
        isAllowEmpty = constraintAnnotation.isAllowEmpty();

        valueList = new ArrayList<>();

        Class<BaseIntEnum> enumClass = constraintAnnotation.enumClazz();

        @SuppressWarnings("rawtypes")
        BaseIntEnum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes") BaseIntEnum enumVal : enumValArr) {
            valueList.add(enumVal.getValue());
        }
    }

}