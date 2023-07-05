package com.sc.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletRequest;

@Data
@EqualsAndHashCode
@Accessors(chain = true)
@ApiModel(value = "UserRegister对象",description = "")
public class UserRegisterParam {
    //String username, String username, String clazzId, String sex,
    //String role, String schoolId, String password,String avatar,String cover, String code, HttpServletRequest request
    @ApiModelProperty(value = "用户名",required = true)
    private String username;
    private String name;
    private String clazzId;
    private String sex;
    private String role;
    private String schoolId;
    private String avatar;
    private String cover;


    @ApiModelProperty(value = "密码",required = true)
    private String password;

    @ApiModelProperty(value = "验证码",required = true)
    private String code;
}
