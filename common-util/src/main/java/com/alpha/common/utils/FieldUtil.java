package com.alpha.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Pattern;

/**
 * Created by chenwen on 16/10/28.
 */
public class FieldUtil {
    public enum Field{
        NAME,
        MOBILE,
        IDCARD,
        EMAIL,
        USERNAME
        ;
    }

    public static Field getFieldByValue(String value){
        if (StringUtils.isEmpty(value)){
            return null;
        }
        if (isMobile(value)){
            return Field.MOBILE;
        }else if (isIdcard(value)){
            return Field.IDCARD;
        }else if (isEmail(value)){
            return Field.EMAIL;
        }else if (isName(value)){
            return Field.NAME;
        }else if (isUsername(value)){
            return Field.USERNAME;
        }
        return null;
    }

    public static boolean isUsername(String username){
        return regex(username,"[0-9\\._A-Za-z]{5,50}");
    }

    public static boolean isName(String value){
        return regex(value,"[\\u4E00-\\u9FA5·]{1,40}");
    }

    public static boolean isMobile(String value){
        return regex(value,"^1[0-9]{10}$");
    }

    public static boolean isIdcard(String value){
        return IdcardUtils.validateCard(value);
    }

    public static boolean isEmail(String value){
        return regex(value,"^[a-z_0-9.-]{1,64}@([a-z0-9-]{1,200}.){1,5}[a-z]{1,6}$");
    }
    public static boolean isNum(String value){
        return regex(value,"^[0-9]+$");
    }

    public static boolean regex(String value,String regex){
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).find();
    }

    public static void main(String[] args) {
        System.out.println(FieldUtil.isName("市场部长"));
    }
}
