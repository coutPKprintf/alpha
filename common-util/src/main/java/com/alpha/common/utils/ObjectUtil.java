package com.alpha.common.utils;

/**
 * Created by chenwen on 16/11/16.
 */
public class ObjectUtil {

    /**
     * 两个对象是否同时为空,两个对象是否同时不为空
     * @param o1
     * @param o2
     * @return
     */
    public static boolean isNullOrNotNull(Object o1,Object o2){
        return isNull(o1,o2) || isNotNull(o1,o2);
    }

    /**
     * 两个对象是否同时为空
     * @param o1
     * @param o2
     * @return
     */
    public static boolean isNull(Object o1,Object o2){
        return o1 == null && o2 == null;
    }

    /**
     * 两个对象是否同时不为空
     * @param o1
     * @param o2
     * @return
     */
    public static boolean isNotNull(Object o1,Object o2){
        return o1 != null && o2 != null;
    }


    /**
     * 获取str值
     * @param object
     * @return
     */
    public static String getStrValue(Object object){
        return object == null ? null : String.valueOf(object);
    }
}
