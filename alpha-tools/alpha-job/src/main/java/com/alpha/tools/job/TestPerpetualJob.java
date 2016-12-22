package com.alpha.tools.job;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by chenwen on 16/11/25.
 * Perpetual作业类型略为复杂，需要继承AbstractIndividualThroughputDataFlowElasticJob并可以指定返回值泛型，该类提供两个方法可覆盖，分别用于抓取和处理数据。
 * 可以获取数据处理成功失败次数等辅助监控信息。需要注意fetchData方法的返回值只有为null或长度为空时，作业才会停止执行，
 * 否则作业会一直运行下去。这点是参照TbSchedule的设计。Perpetual作业类型更适用于流式不间歇的数据处理。
 *
 * 作业执行时会将fetchData的数据传递给processData处理，其中processData得到的数据是通过多线程（线程池大小可配）拆分的。
 * 建议processData处理数据后，更新其状态，避免fetchData再次抓取到，从而使得作业永远不会停止。processData的返回值用于表示数据是否处理成功，
 * 抛出异常或者返回false将会在统计信息中归入失败次数，返回true则归入成功次数。
 */
@Slf4j
public class TestPerpetualJob implements DataflowJob<Long> {
    @Override
    public List<Long> fetchData(ShardingContext shardingContext) {
        log.info("item = {}" , shardingContext.getShardingItem());
        return null;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<Long> list) {
        log.info("list = {}",list);
    }
}
