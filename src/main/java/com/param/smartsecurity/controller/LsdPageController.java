package com.param.smartsecurity.controller;


import com.param.smartsecurity.common.RestMessage;
import com.param.smartsecurity.entity.LsdPage;
import com.param.smartsecurity.utils.TimeUtls;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 *
 * @author Mc_liao
 * @since 2020-11-02
 */
@RestController
@RequestMapping("/lsd-page")
@Api(value = "页面配置",tags = "页面配置")
public class LsdPageController {

    @ApiOperation(value = "puttest",notes = "puttest测试说明2")
    @PostMapping(value = "/puttest")
    public RestMessage puttest(@RequestBody @Valid LsdPage lsdPage){
        return new RestMessage(lsdPage);
    }


    @ApiOperation(value = "定时任务",notes = "定时任务")
    @GetMapping(value = "/get")
    public RestMessage get(){
        RestMessage restMessage = new RestMessage();
        restMessage.setSuccess(true);
        System.out.println(TimeUtls.createTime());
        return restMessage;
    }
}

