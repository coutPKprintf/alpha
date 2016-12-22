package com.alpha.common.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chenwen on 16/11/2.
 */
public class AreaCodeUtil {
    public static final String ALL = "000000";

    /**
     * 是否存在这个地区
     * @param id 地区编号
     * @param areaCodeMap 区域字典表
     * @return
     */
    public static boolean isExists(Integer id,Map<Integer,Boolean> areaCodeMap){
        if (areaCodeMap.containsKey(0)){
            return true;
        }

        AreaCodeEnum areaCodeEnum = areaCode(id);

        if (areaCodeEnum == AreaCodeEnum.ALL){
            return false;
        }

        String idStr = String.valueOf(id);
        if (areaCodeEnum == AreaCodeEnum.PROVINCE){
            return false;
        }else {
            if (areaCodeEnum == AreaCodeEnum.CITY){
                return areaCodeMap.containsKey(Integer.parseInt(idStr.substring(0,2) + "0000"));
            }else {
                return areaCodeMap.containsKey(Integer.parseInt(idStr.substring(0,4) + "00")) || areaCodeMap.containsKey(Integer.parseInt(idStr.substring(0,2) + "0000"));
            }
        }
    }

    /**
     * 转换地区编号,地区去重
     * @param areaCode
     * @return
     */
    public static Set<String> toStringAreaCode(List<Integer> areaCode){
        Map<Integer,Boolean> areaCodeMap = totAreaCodeMap(areaCode);
        Set<String> result = new HashSet<>();
        for(Integer id : areaCode){
            if (id == 0){
                return new HashSet<String>(){{add(ALL);}};
            }
            if (!isExists(id,areaCodeMap)){
                result.add(String.valueOf(id));
            }
        }
        return result;
    }

    /**
     * 地区编号转换
     * @param areaCode
     * @return
     */
    public static Map<Integer,Boolean> totAreaCodeMap(List<Integer> areaCode){
        Map<Integer,Boolean> areaCodeMap = new HashMap<>();
        for(Integer id : areaCode){
            areaCodeMap.put(id,true);
        }
        return areaCodeMap;
    }

    public static AreaCodeEnum areaCode(Integer areaCode){
        if (areaCode == 0){
            return AreaCodeEnum.ALL;
        }
        return areaCode(String.valueOf(areaCode));
    }

    public static AreaCodeEnum areaCode(String areaCode){
        if (areaCode.endsWith(ALL)){
            return AreaCodeEnum.ALL;
        }else if (areaCode.endsWith("0000")){
            return AreaCodeEnum.PROVINCE;
        }else if (areaCode.endsWith("00")){
            return AreaCodeEnum.CITY;
        }
        return AreaCodeEnum.AREA;
    }


    /**
     * 地区枚举,省,市,区,所有
     */
    public enum AreaCodeEnum{
        PROVINCE,
        CITY,
        AREA,
        ALL
    }
}
