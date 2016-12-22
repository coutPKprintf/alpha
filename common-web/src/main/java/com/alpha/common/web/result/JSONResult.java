package com.alpha.common.web.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alpha.common.annotation.IPropertyFilter;
import com.alpha.common.annotation.JSONPropertyFilter;
import com.alpha.common.exception.IErrorCode;
import com.alpha.common.utils.JacksonUtil;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collection;

/**
 * Created by cw on 15-11-20.
 */
public class JSONResult extends Result<JSONObject> {
    private JSONResult(IErrorCode iErrorCode, boolean success, JSONObject data) {
        this(iErrorCode.getCode(), iErrorCode.getMessage(),success,data);
    }

    private JSONResult(String code, String message, boolean success, JSONObject data) {
        super.setCode(code);
        super.setMessage(message);
        super.setSuccess(success);
        super.setData(data);
    }

    public static JSONResult build(String code, String message, boolean success, JSONObject data){
        return new JSONResult(code,message,success,data);
    }

    public static JSONResult ok() {
        return ok(new JSONObject());
    }

    public static JSONResult ok(Object object){
        return ok(JSON.parseObject(toJSONString(object,null)));
    }

    public static JSONResult ok(Object object, IPropertyFilter propertyFilter){
        return ok(JSON.parseObject(toJSONString(object,propertyFilter)));
    }

    public static JSONResult ok(JSONObject data) {
        return new JSONResult(IErrorCode.OK, true, JSON.parseObject(toJSONString(data,null)));
    }

    //服务异常
    public static JSONResult error() {
        return error(IErrorCode.ERROR);
    }

    public static JSONResult error(IErrorCode iErrorCode) {
        return error(iErrorCode, new JSONObject());
    }

    public static JSONResult error(IErrorCode iErrorCode, JSONObject data) {
        return new JSONResult(iErrorCode, false, data);
    }

    public static JSONResult error(String code, String message) {
        return new JSONResult(code, message, false, new JSONObject());
    }

    public static JSONResult custom(String code, String message, boolean success, JSONObject data){
        return new JSONResult(code,message,success,data);
    }

    public JSONResult put(String key, Object value) {
        if (super.getData() == null) {
            super.setData(new JSONObject());
        }
        super.getData().put(key, value);
        return this;
    }

    public JSONResult propertyFilter(IPropertyFilter propertyFilter){
        if (getData() != null){
            super.setData(JSON.parseObject(toJSONString(getData(),propertyFilter)));
        }
        return this;
    }

    public ModelAndView buildModelAndView() {
        return new ModelAndView(new MappingJackson2JsonView(), JacksonUtil.jsonToMap(this.buildJsonString()));
    }

    public String buildJsonString() {
        return JacksonUtil.objToJson(this);
    }

    private static String toJSONString(Object object,IPropertyFilter propertyFilter){
        if (object == null){
            return new JSONObject().toJSONString();
        }else if (object instanceof Collection){
            JSONObject result = new JSONObject();
            result.put("list",object);
            if (propertyFilter == null){
                return JSON.toJSONString(object);
            }
            return JSON.toJSONString(result,new JSONPropertyFilter(propertyFilter));
        } else {
            if (propertyFilter == null){
                return JSON.toJSONString(object);
            }
            return JSON.toJSONString(object, new JSONPropertyFilter(propertyFilter));
        }
    }
}
