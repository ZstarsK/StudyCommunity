package com.sc.controller;

import com.sc.service.UserService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.UserLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "登录之后返回 token")
    @PostMapping("/common/login")
    public ResultBean login(@RequestBody UserLoginParam  userLoginParam, HttpServletRequest request){
        if (userLoginParam != null){
            return userService.login(userLoginParam.getUsername(),userLoginParam.getPassword(),request);
        }
        return ResultBean.error("用户名或密码不能为空！");

    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/common/logout")
    public ResultBean logout(){
        return ResultBean.success("退出登录!");
    }
}
