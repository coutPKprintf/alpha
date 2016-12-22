package com.alpha.common.invoke;

import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;

/**
 * Created by chenwen on 16/10/20.
 */
public final class MethodCacheKey {
    private final Method method;
    private final Class<?> targetClass;

    public MethodCacheKey(Method method, Class<?> targetClass) {
        Assert.notNull(method, "method must be set.");
        this.method = method;
        this.targetClass = targetClass;
    }

    public boolean equals(Object other) {
        if(this == other) {
            return true;
        } else if(!(other instanceof MethodCacheKey)) {
            return false;
        } else {
            MethodCacheKey otherKey = (MethodCacheKey)other;
            return this.method.equals(otherKey.method) && ObjectUtils.nullSafeEquals(this.targetClass, otherKey.targetClass);
        }
    }

    public int hashCode() {
        return this.method.hashCode() + (this.targetClass != null?this.targetClass.hashCode() * 29:0);
    }
}
