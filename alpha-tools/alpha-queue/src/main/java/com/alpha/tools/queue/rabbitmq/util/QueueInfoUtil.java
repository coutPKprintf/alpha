package com.alpha.tools.queue.rabbitmq.util;

import com.alpha.tools.queue.rabbitmq.ExchangeEnum;
import com.alpha.tools.queue.rabbitmq.QueueInfo;

/**
 * Created by chenwen on 16/9/20.
 */
public class QueueInfoUtil {
    public static String getBindRouteKey(QueueInfo queueInfo){
        return String.format("%s|%s|%s",queueInfo.getRouteKey(),queueInfo.getExchangeName(),queueInfo.getExchangeType().getValue());
    }

    public static String getBindKey(QueueInfo queueInfo){
        return String.format("%s|%s|%s",queueInfo.getQueueName(),queueInfo.getExchangeName(),queueInfo.getExchangeType().getValue());
    }

    public static QueueInfo getQueueInfo(String bindKey){
        String[] keys = bindKey.split("\\|");
        return QueueInfo.builder().queueName(keys[0]).exchangeName(keys[1]).exchangeType(ExchangeEnum.getEnumByValue(keys[2])).build();
    }
}
