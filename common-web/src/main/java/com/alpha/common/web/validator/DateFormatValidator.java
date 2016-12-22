package com.alpha.common.web.validator;

import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cw on 15-11-30.
 */
public class DateFormatValidator implements ConstraintValidator<DateFormat, String> {
    String format;

    boolean allowEmpty;

    boolean lessCurrent;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (StringUtils.isEmpty(value)){
            return allowEmpty;
        }

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

            Date date = simpleDateFormat.parse(value);

            if (lessCurrent && date.after(new Date())){
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

        return true;
    }

    @Override
    public void initialize(DateFormat constraintAnnotation) {
        format = constraintAnnotation.value();
        allowEmpty = constraintAnnotation.allowEmpty();
        lessCurrent = constraintAnnotation.lessCurrent();
    }

}