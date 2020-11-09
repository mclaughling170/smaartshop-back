package com.param.smartsecurity.controller;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicParameters;
import com.param.smartsecurity.common.RestMessage;
import com.param.smartsecurity.domain.ReqEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Mc QiLing
 * @Date 2020/11/2 3:22 下午
 * @Version 1.0
 */
@Api(value = "测试",tags = "测试用例")
@RestController
@RequestMapping("/api/test")
public class Test {

    @ApiOperation(value = "puttest",notes = "puttest测试说明2")
    @GetMapping(value = "/puttest")
    @ApiImplicitParam(value = "code",name = "code",dataType = "string",paramType = "query",defaultValue = "abc")
    public RestMessage puttest(String code){
        return new RestMessage(code);
    }

    @ApiOperation(value = "deletetest",notes = "deletetest测试说明2")
    @DeleteMapping(value = "/deletetest")
    @ApiImplicitParam(value = "code",name = "code",dataType = "string",paramType = "query",example = "adasdasd")
    public RestMessage deletetest(@RequestParam(value = "code") String code){
        return new RestMessage(code);
    }

    @PostMapping("/reqbody")
    @ApiOperation(value = "RequestBody接口类型",notes = "RequestBody测试接口,实体类型")
    public RestMessage reqbody(@RequestBody ReqEntity reqEntity){
        return new RestMessage(reqEntity);
    }
    @PostMapping("/reqbody2")
    @ApiOperation(value = "RequestBody接口类型2",notes = "RequestBody测试接口2-string类型")
    public RestMessage reqbody2(@RequestBody String reqEntity){
        return new RestMessage(reqEntity);
    }

    @PostMapping("/reqbody1")
    @ApiOperation(value = "ModelAttribute",notes = "ModelAttribute类型参数")
    public RestMessage reqbody1(@ModelAttribute ReqEntity reqEntity){
        return new RestMessage(reqEntity);
    }

    @PostMapping("/reqbody3")
    @ApiOperation(value = "header参数",notes = "header参数")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "code",name = "code",dataType = "string",paramType = "query"),
            @ApiImplicitParam(value = "headerparam",name = "headerparam",dataType = "string",paramType = "header"),
            @ApiImplicitParam(value = "page",name = "page",dataType = "int",paramType = "query"),
            @ApiImplicitParam(value = "page1",name = "page1",dataType = "Long",paramType = "query")
    })
    public RestMessage reqbody3(@RequestHeader(value = "headerparam") String headerparam,@RequestParam(value = "code") String code,
                                @RequestParam(value = "page") int page,@RequestParam(value = "page1") Long page1){
        return new RestMessage(MapUtil.builder("code",code).put("header",headerparam).put("page",String.valueOf(page)).put("page1",String.valueOf(page1)).build());
    }

    @GetMapping("obj1")
    @ApiOperation(value = "动态参数",notes = "获取用户")
    // 设置动态参数描述注解
    @DynamicParameters(name = "map",properties = {
            @DynamicParameter(name = "id",value = "id",example = "1",required =true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "userName",value = "用户名",required =true),
            @DynamicParameter(name = "mobile",value = "手机号"),
    })
    public RestMessage testUse(JSONObject map){
        return new RestMessage();
    }

}
