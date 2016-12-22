package com.alpha.common.web.fastjson;


import com.alpha.common.web.util.HttpUtils;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chenwen on 16/10/10.
 */
public class FastJsonArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(FastJson.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        return HttpUtils.getModelFromRequest(webRequest.getNativeRequest(HttpServletRequest.class),parameter.getParameterType());
        //HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);


        // content-type不是json的不处理
//        if (!request.getContentType().contains("application/json")) {
//            return null;
//        }
//
//        // 把reqeust的body读取到StringBuilder
//        BufferedReader reader = request.getReader();
//        StringBuilder sb = new StringBuilder();
//
//        char[] buf = new char[1024];
//        int rd;
//        while((rd = reader.read(buf)) != -1){
//            sb.append(buf, 0, rd);
//        }
//
//        // 利用fastjson转换为对应的类型
//        if(JSONObjectWrapper.class.isAssignableFrom(parameter.getParameterType())){
//            return new JSONObjectWrapper(JSON.parseObject(sb.toString()));
//        } else {
//           JSON.parseObject(sb.toString(), parameter.getParameterType());
//            Object object =JSON.parseObject(sb.toString(), parameter.getParameterType());
//            HttpUtils.validate(object,parameter.getParameterType());
//            return object;
//        }
    }
}

