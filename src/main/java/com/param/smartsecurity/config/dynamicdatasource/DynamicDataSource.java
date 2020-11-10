package com.param.smartsecurity.config.dynamicdatasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Author Mc QiLing
 * @Date 2020/11/9 4:10 下午
 * @Version 1.0
 */

@Slf4j
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        String datasource = DataSourceContextHolder.getDbType();
        log.debug("当前使用数据源:{}", datasource);
        return datasource;
    }

}