package com.param.smartsecurity.task;

import com.param.smartsecurity.utils.TimeUtls;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

/**
 * @author paramland
 */
@Slf4j
public class MyJob implements Job {


  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    log.info("任务开始执行了");
    try {
      executeTask();
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    log.info("任务执行结束了");
  }

  private void executeTask() throws SchedulerException {
      log.info("今天是个好日子+执行时间："+TimeUtls.createTime());
  }


}