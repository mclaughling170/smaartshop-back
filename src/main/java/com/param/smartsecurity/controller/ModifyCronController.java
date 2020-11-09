package com.param.smartsecurity.controller;

import com.param.smartsecurity.manager.QuartzManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author paramland
 */
@RestController
@Api(value = "定时任务控制",tags = "定时任务控制")
public class ModifyCronController {
 
    @Autowired
    private QuartzManager quartzManager;

    @GetMapping("/modify")
    @ApiOperation(value = "修改定时任务时间",notes = "修改定时任务时间")
    public String modify() throws SchedulerException {
        /**10秒执行一次*/
        String cron="*/10 * * * * ?";
        quartzManager.pauseJob(QuartzManager.JOB1,QuartzManager.GROUP1);
        quartzManager.modifyJob(QuartzManager.JOB1,QuartzManager.GROUP1,cron);
        return "ok";
    }

    @GetMapping("/stop")
    @ApiOperation(value = "停止定时任务",notes = "停止定时任务")
    public String stop() throws SchedulerException {
        /**10秒执行一次*/
        quartzManager.pauseAllJob();
        return "ok";
    }


    @GetMapping("/resumeAllJob")
    @ApiOperation(value = "恢复定时任务",notes = "恢复定时任务")
    public String resumeAllJob() throws SchedulerException {
        /**10秒执行一次*/
        quartzManager.resumeAllJob();
        return "ok";
    }
}
