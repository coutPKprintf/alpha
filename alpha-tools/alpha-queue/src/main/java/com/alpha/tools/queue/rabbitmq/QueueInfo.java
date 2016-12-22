package com.alpha.tools.queue.rabbitmq;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by chenwen on 16/9/20.
 */
@Builder
public class QueueInfo implements Serializable{
    /**
     * serial version
     */
    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private String queueName;

    @Getter
    @Setter
    private String exchangeName;

    @Setter
    private String routeKey;

    @Setter
    private Boolean durable;

    @Setter
    private Boolean exclusive;

    @Setter
    private Boolean autoDelete;

    @Setter
    private ExchangeEnum exchangeType = ExchangeEnum.DIRECT;

    public ExchangeEnum getExchangeType(){
        return exchangeType = exchangeType == null ? ExchangeEnum.DIRECT : exchangeType;
    }

    public String getRouteKey() {
        switch (getExchangeType()){
            case DIRECT:
                routeKey = routeKey == null ? queueName : routeKey;
                break;
            default:
                routeKey = routeKey == null ? exchangeName : routeKey;
        }
        return routeKey;
    }

    public Boolean getDurable() {
        return durable = durable == null ? true : durable;
    }

    public Boolean getExclusive() {
        return exclusive = exclusive == null ? false : exclusive;
    }

    public Boolean getAutoDelete() {
        return autoDelete = autoDelete == null ? false : autoDelete;
    }
}
