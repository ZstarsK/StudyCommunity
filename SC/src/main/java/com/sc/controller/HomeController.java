package com.sc.controller;

import com.sc.entity.Post;
import com.sc.service.PostService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.UserLoginParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "HomeController")
@RestController



public class HomeController {
    @Autowired
    private PostService postService;

    @ApiOperation(value = "登录之后跳转主页")
    @PostMapping("/common/home")
    public ResultBean home(@RequestBody UserLoginParam userLoginParam, HttpServletRequest request){


        return ResultBean.success("信息获取成功");
    }
}
