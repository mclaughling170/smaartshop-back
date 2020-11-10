package com.param.smartsecurity.entity;

import com.param.smartsecurity.entity.basic.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Mc_liao
 * @since 2020-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Test对象", description="测试对象事物")
public class Test extends BaseEntity<Test> implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "名称")
    private String pwd;

}
