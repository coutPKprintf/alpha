package com.alpha.tools.job;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.schedule.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Created by chenwen on 16/11/29.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Configuration
public class TestDataflowJobConfig {
    @Resource
    private ZookeeperRegistryCenter regCenter;

    @Value("${dataflowJob.testPerpetualJob.name}")
    private String testPerpetualJobName;

    @Value("${dataflowJob.testPerpetualJob.shardingTotalCount}")
    private int testPerpetualJobShardingTotalCount;

    @Value("${dataflowJob.testPerpetualJob.cron}")
    private String testPerpetualJobCron;

    @Value("${dataflowJob.testPerpetualJob.shardingItemParameters}")
    private String testPerpetualJobShardingItemParameters;

    @Bean
    public DataflowJob dataflowJob() {
        return new TestPerpetualJob();
    }

    @Bean(value = "testPerpetualJob",initMethod = "init")
    public JobScheduler testPerpetualJobScheduler(final DataflowJob dataflowJob){
        return new SpringJobScheduler(dataflowJob ,regCenter, liteJobConfiguration(),new ElasticJobListener[0]);
    }

    public LiteJobConfiguration liteJobConfiguration(){
        // 定义作业核心配置

        // 定义DATAFLOW类型配置

        // 定义Lite作业根配置
        return LiteJobConfiguration.newBuilder(new DataflowJobConfiguration(JobCoreConfiguration.newBuilder(testPerpetualJobName, testPerpetualJobCron, testPerpetualJobShardingTotalCount)
                .shardingItemParameters(testPerpetualJobShardingItemParameters)
                .build(), TestDataflowJobConfig.class.getCanonicalName(), true)).overwrite(true).build();
    }
}
