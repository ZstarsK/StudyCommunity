package com.sc.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户信息修改实体类
 */
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "UserInfoUpdate对象",description = "")
public class UserInfoUpdateParam {

    @ApiModelProperty(value = "用户ID",required = true)
    private Integer id;

    @ApiModelProperty(value = "用户昵称",required = true)
    private String name;

    @ApiModelProperty(value = "用户性别",required = true)
    private String gender;

    @ApiModelProperty(value = "用户城市",required = true)
    private String city;

    @ApiModelProperty(value = "用户个人介绍",required = true)
    private String introduce;
}
