package com.alpha.common.invoke;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenwen on 16/10/20.
 */

public class ParameterEvaluationContext extends StandardEvaluationContext {
    private final ParameterNameDiscoverer paramDiscoverer;
    private final Method method;
    private final Object[] args;
    private final Class<?> targetClass;
    private Map<MethodCacheKey, Method> methodCache;
    private final List<String> unavailableVariables;
    private boolean paramLoaded = false;

    public ParameterEvaluationContext(org.springframework.core.LocalVariableTableParameterNameDiscoverer paramDiscoverer, Method method, Object[] args, Class<?> targetClass) {
        this.paramDiscoverer = paramDiscoverer;
        this.method = method;
        this.args = args;
        this.targetClass = targetClass;
        this.unavailableVariables = new ArrayList();
        this.methodCache = new HashMap();
    }

    public void addUnavailableVariable(String name) {
        this.unavailableVariables.add(name);
    }

    public Object lookupVariable(String name) {
        if(this.unavailableVariables.contains(name)) {
            throw new VariableNotAvailableException(name);
        } else {
            Object variable = super.lookupVariable(name);
            if(variable != null) {
                return variable;
            } else {
                if(!this.paramLoaded) {
                    this.loadArgsAsVariables();
                    this.paramLoaded = true;
                    variable = super.lookupVariable(name);
                }

                return variable;
            }
        }
    }

    private void loadArgsAsVariables() {
        if(!ObjectUtils.isEmpty(this.args)) {
            MethodCacheKey methodKey = new MethodCacheKey(this.method, this.targetClass);
            Method targetMethod = (Method)this.methodCache.get(methodKey);
            if(targetMethod == null) {
                targetMethod = AopUtils.getMostSpecificMethod(this.method, this.targetClass);
                if(targetMethod == null) {
                    targetMethod = this.method;
                }

                this.methodCache.put(methodKey, targetMethod);
            }

            for(int parameterNames = 0; parameterNames < this.args.length; ++parameterNames) {
                this.setVariable("a" + parameterNames, this.args[parameterNames]);
                this.setVariable("p" + parameterNames, this.args[parameterNames]);
            }

            String[] var5 = this.paramDiscoverer.getParameterNames(targetMethod);
            if(var5 != null) {
                for(int i = 0; i < var5.length; ++i) {
                    this.setVariable(var5[i], this.args[i]);
                }
            }

        }
    }
}
