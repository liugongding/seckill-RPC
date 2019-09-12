package com.dingding.seckill.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import io.seata.spring.annotation.GlobalTransactionScanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-08
 */
@Configuration
public class SeataTestConfig {

    @Value("${spring.datasource.druid.url}")
    private String url;

    @Value("${spring.datasource.druid.username}")
    private String username;

    @Value("${spring.datasource.druid.password}")
    private String password;

    @Value("${spring.datasource.druid.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.druid.initial-size}")
    private int initialSize;

    @Value("${spring.datasource.druid.min-idle}")
    private int minIdle;

    @Value("${spring.datasource.druid.max-active}")
    private int maxActive;

    @Value("${spring.datasource.druid.max-wait}")
    private int maxWait;

    @Value("${spring.datasource.druid.time-between-eviction-runs-millis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @Value("@{mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${spring.application.name}")
    private String applicationId;

    @Bean
    public DataSourceProxy dataSourceProxy() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(this.url);
        druidDataSource.setUsername(this.username);
        druidDataSource.setPassword(this.password);
        druidDataSource.setDriverClassName(this.driverClassName);
        druidDataSource.setInitialSize(this.initialSize);
        druidDataSource.setMinIdle(this.minIdle);
        druidDataSource.setMaxActive(this.maxActive);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenConnectErrorMillis(this.timeBetweenEvictionRunsMillis);
        return new DataSourceProxy(druidDataSource);
    }

    @Bean
    public GlobalTransactionScanner globalTransactionScanner() {
        GlobalTransactionScanner globalTransactionScanner = new GlobalTransactionScanner(this.applicationId, "my_group");
        return globalTransactionScanner;
    }
}
