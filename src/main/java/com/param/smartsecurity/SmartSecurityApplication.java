package com.param.smartsecurity;


import com.param.smartsecurity.factory.MyJobFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author paramland
 */
@SpringBootApplication
@MapperScan("com.param.smartsecurity.mapper")
@EnableScheduling
@EnableTransactionManagement
public class SmartSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartSecurityApplication.class, args);
    }

    @Autowired
    private MyJobFactory myJobFactory;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(myJobFactory);
        return schedulerFactoryBean;
    }
    @Bean
    public Scheduler schedulerQuery() {
        return schedulerFactoryBean().getScheduler();
    }

}
