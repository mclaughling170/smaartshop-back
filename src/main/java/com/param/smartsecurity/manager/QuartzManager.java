package com.param.smartsecurity.manager;

import com.param.smartsecurity.task.My2Job;
import com.param.smartsecurity.task.MyJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;


/**
 * 定义QuartzManager管理我们的定时任务方法
 * @author paramland
 */
@Configuration
public class QuartzManager {
 
    public static final String JOB1="job1";
    public static final String GROUP1="group1";

    public static final String JOB2="job2";
    //public static final String DEFAULT_CRON="0 0 1 ? * L";
    /**
     * 默认5秒执行一次
     */
    public static final String DEFAULT_CRON="*/5 * * * * ?";
 
    /**
     * 任务调度
     */
    @Autowired
    private Scheduler scheduler;
 
    /**
     * 开始执行定时任务
     */
    public void startJob() throws SchedulerException {
        startJobTask(scheduler);
        scheduler.start();
    }
 
    /**
     * 启动定时任务
     * @param scheduler
     */
    private void startJobTask(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail= JobBuilder.newJob(MyJob.class).withIdentity(JOB1,GROUP1).build();
        JobDetail jobDetail2= JobBuilder.newJob(My2Job.class).withIdentity(JOB2,GROUP1).build();
        CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule(DEFAULT_CRON);
        CronTrigger cronTrigger=TriggerBuilder.newTrigger().withIdentity(JOB1,GROUP1)
                .withSchedule(cronScheduleBuilder).build();
        CronTrigger cronTrigger2=TriggerBuilder.newTrigger().withIdentity(JOB2,GROUP1)
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail,cronTrigger);
        scheduler.scheduleJob(jobDetail2,cronTrigger2);
    }


    /**
     * 获取Job信息
     * @param name
     * @param group
     */
    public String getjobInfo(String name,String group) throws SchedulerException {
        TriggerKey triggerKey=new TriggerKey(name,group);
        CronTrigger cronTrigger= (CronTrigger) scheduler.getTrigger(triggerKey);
        return String.format("time:%s,state:%s",cronTrigger.getCronExpression(),
                scheduler.getTriggerState(triggerKey).name());
    }
 
    /**
     * 修改任务的执行时间
     * @param name
     * @param group
     * @param cron cron表达式
     * @return
     * @throws SchedulerException
     */
    public boolean modifyJob(String name,String group,String cron) throws SchedulerException{
        Date date=null;
        TriggerKey triggerKey=new TriggerKey(name, group);
        CronTrigger cronTrigger= (CronTrigger) scheduler.getTrigger(triggerKey);
        String oldTime=cronTrigger.getCronExpression();
        if (!oldTime.equalsIgnoreCase(cron)){
            CronScheduleBuilder cronScheduleBuilder=CronScheduleBuilder.cronSchedule(cron);
            CronTrigger trigger=TriggerBuilder.newTrigger().withIdentity(name,group)
                    .withSchedule(cronScheduleBuilder).build();
            date=scheduler.rescheduleJob(triggerKey,trigger);
        }
        return date !=null;
    }
 
    /**
     * 暂停所有任务
     * @throws SchedulerException
     */
    public void pauseAllJob()throws SchedulerException{
        scheduler.pauseAll();
    }
 
    /**
     * 暂停某个任务
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void pauseJob(String name,String group)throws SchedulerException{
        JobKey jobKey=new JobKey(name,group);
        JobDetail jobDetail=scheduler.getJobDetail(jobKey);
        if (jobDetail==null){
            return;
        }
        scheduler.pauseJob(jobKey);
    }
 
    /**
     * 恢复所有任务
     * @throws SchedulerException
     */
    public void resumeAllJob()throws SchedulerException{
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     */
    public void resumeJob(String name,String group)throws SchedulerException{
        JobKey jobKey=new JobKey(name,group);
        JobDetail jobDetail=scheduler.getJobDetail(jobKey);
        if (jobDetail==null){
            return;
        }
        scheduler.resumeJob(jobKey);
    }
 
    /**
     * 删除某个任务
     * @param name
     * @param group
     * @throws SchedulerException
     */
    public void deleteJob(String name,String group)throws SchedulerException{
        JobKey jobKey=new JobKey(name, group);
        JobDetail jobDetail=scheduler.getJobDetail(jobKey);
        if (jobDetail==null){
            return;
        }
        scheduler.deleteJob(jobKey);
    }
 
}
