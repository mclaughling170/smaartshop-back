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
public class My2Job implements Job {


  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    log.info("第二份工作开始执行了");
    try {
      executeTask();
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    log.info("第二份工作执行结束了");
  }

  private void executeTask() throws SchedulerException {
      log.info("第二份工作+执行时间："+TimeUtls.createTime());
  }


}