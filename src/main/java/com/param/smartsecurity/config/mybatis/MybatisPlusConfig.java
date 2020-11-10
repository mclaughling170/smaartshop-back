package com.param.smartsecurity.config.mybatis;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.param.smartsecurity.config.dynamicdatasource.CommonEnum;
import com.param.smartsecurity.config.dynamicdatasource.DynamicDataSource;
import com.param.smartsecurity.utils.MyMetaObjectHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;



/**
 * @Author Mc QiLing
 * @Date 2020/11/9 4:10 下午
 * @Version 1.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.param.smartsecurity.mapper.*")
public class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }


    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource master() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "db2")
    @ConfigurationProperties(prefix = "spring.datasource.druid.db2")
    public DataSource db2() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 动态数据源配置
     *
     * @return 数据源
     */
    @Bean(name = "multipleDataSource")
    @Primary
    public DataSource multipleDataSource(@Qualifier("master") DataSource db1,
                                         @Qualifier("db2") DataSource db2) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> targetDataSources = new HashMap<>(16);
        targetDataSources.put(CommonEnum.DB1.getValue(), db1);
        targetDataSources.put(CommonEnum.DB2.getValue(), db2);
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 程序默认数据源，根据程序调用数据源频次，把常调用的数据源作为默认
        dynamicDataSource.setDefaultTargetDataSource(db1);
        return dynamicDataSource;
    }

    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(multipleDataSource(master(), db2()));
        // 设置默认需要扫描的 xml 文件
        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
		//其他配置项
        MybatisConfiguration configuration = new MybatisConfiguration();
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        // 驼峰和下划线转换
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setCacheEnabled(false);
        configuration.setCallSettersOnNulls(true);
        sqlSessionFactory.setConfiguration(configuration);
        sqlSessionFactory.setTypeAliasesPackage("com.param.smartsecurity.entity");
        // 数据库查询结果驼峰式返回
        sqlSessionFactory.setObjectWrapperFactory(new MybatisMapWrapperFactory());
        // 添加分页功能
        sqlSessionFactory.setPlugins(new Interceptor[]{
                paginationInterceptor()
        });
        // 实现自动填充功能
        sqlSessionFactory.setGlobalConfig(globalConfiguration());
        return sqlSessionFactory.getObject();
    }

    @Bean(name = "multipleTransactionManager")
    @Primary
    public DataSourceTransactionManager multipleTransactionManager(@Qualifier("multipleDataSource") DataSource dataSource) {
    	// 动态事务配置
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public GlobalConfig globalConfiguration() {
    	// 自动填充创建时间和更新时间（MyMetaObjectHandler的实现参考 “MyBatis Plus 的自动填充功能” 博客）
        GlobalConfig conf = new GlobalConfig();
        conf.setMetaObjectHandler(new MyMetaObjectHandler());
        return conf;
    }

}