package com.sc.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录实体类
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "UserLogin对象",description = "")
public class UserLoginParam {

    @ApiModelProperty(value = "手机号",required = true)
    private String phonenum;

    @ApiModelProperty(value = "密码",required = true)
    private String password;
}