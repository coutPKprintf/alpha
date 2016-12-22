package com.alpha.common.invoke;

import org.springframework.expression.EvaluationException;

/**
 * Created by chenwen on 16/10/20.
 */
public class VariableNotAvailableException extends EvaluationException {
    private final String name;

    public VariableNotAvailableException(String name) {
        super("Variable \'" + name + "\' is not available");
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
