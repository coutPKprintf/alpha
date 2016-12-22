package com.alpha.common.enums;

import java.io.Serializable;

/**
 * Created by chenwen on 16/10/19.
 */
public interface BaseIntEnum extends Serializable{
    public final static long serialVersionUID = 1l;

    int getValue();
    String getName();
}
