package com.sc.controller;


import com.sc.service.UserService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.UserRegisterParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 注册
 */

@Api(tags = "RegisterController")
@RestController
public class RegisterController {

    @Autowired
    private UserService userService;
    @ApiOperation(value = "注册")
    @PostMapping("/common/register")
    public ResultBean register(@RequestBody UserRegisterParam userRegisterParam, HttpServletRequest request){
        if (userRegisterParam != null){
            return userService.register(userRegisterParam.getPhonenum(),userRegisterParam.getUsername(),userRegisterParam.getClazz_id(),
                    userRegisterParam.getSex(),
                    userRegisterParam.getRole(),
                    userRegisterParam.getSchool_id(),
                    userRegisterParam.getPassword(),
                    userRegisterParam.getAvatar(),
                    userRegisterParam.getCover(),
                    userRegisterParam.getCode(),request);
        }
        return ResultBean.error("用户名密码不能为空！");
    }
}
