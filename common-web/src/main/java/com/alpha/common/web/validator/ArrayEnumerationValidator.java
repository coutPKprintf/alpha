package com.alpha.common.web.validator;

import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 15-11-30.
 */
public class ArrayEnumerationValidator implements ConstraintValidator<ArrayEnumeration, JSONArray> {

    List<String> valueList = null;

    @Override
    public void initialize(ArrayEnumeration arrayEnumeration) {
        valueList = new ArrayList<>();
        Class<? extends Enum<?>> enumClass = arrayEnumeration.enumClazz();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = enumClass.getEnumConstants();

        for (@SuppressWarnings("rawtypes")
        Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }
    }

    @Override
    public boolean isValid(JSONArray value, ConstraintValidatorContext context) {
        if (value == null){
            return false;
        }
        for(Object object:value){
            if (!valueList.contains(object.toString().toUpperCase())){
                return false;
            }
        }
        return true;
    }

}