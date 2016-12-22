package com.alpha.tools.queue.rabbitmq;

import java.io.Serializable;

/**
 * Created by cw on 16-1-12.
 */
public enum ExchangeEnum implements Serializable{
    DIRECT("direct"),
    TOPIC("topic"),
    FANOUT("fanout"),
    HEADERS("headers"),
    PSQL("psql");

    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    private final String strValue;

    ExchangeEnum(String strValue) {
        this.strValue = strValue;
    }

    public String getValue() {
        return strValue;
    }

    public static ExchangeEnum getEnumByValue(String strValue) {
        for (ExchangeEnum v : values()) {
            if (v.getValue().equals(strValue)) {
                return v;
            }
        }
        return null;
    }
    public static ExchangeEnum getEnumByStartWithValue(String strValue) {
        for (ExchangeEnum v : values()) {
            if (v.getValue().startsWith(strValue)) {
                return v;
            }
        }
        return null;
    }

}
