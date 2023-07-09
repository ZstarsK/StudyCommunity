package com.sc.controller;


import com.sc.Util.GetUserByToken;
import com.sc.entity.User;
import com.sc.vo.ResultBean;
import com.sc.service.UserService;
import com.sc.vo.param.UserInfoUpdateParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author sc
 * @since 2022/04/17 12:51
 */

@Api(tags = "UserController")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${avatar_storage.ip}")
    private String ip;

    @Value("${avatar_storage.port}")
    private String port;

    @Value("${avatar_storage.path}")
    private String porPath;

    @Autowired
    private GetUserByToken getUserByToken;


    @ApiOperation(value = "获取当前登录用户信息")
    @GetMapping("/common/user/info")
    public User getUserInfo(Principal principal){
        System.out.println("principal = " + principal.toString());

        String username = principal.getName();
        User user = userService.getUserByUsername(username);
        user.setPassword(null);
        return user;
    }

    @ApiOperation(value = "修改当前用户信息")
    @PutMapping("/user/updateUserinfo")
    public ResultBean updateUserinfo(@RequestBody UserInfoUpdateParam userInfoUpdateParam, HttpServletRequest request){
        return userService.updateUserinfo(userInfoUpdateParam);
    }

    @ApiOperation(value = "通过用户ID获取用户信息")
    @GetMapping("/getUserinfoById")
    public ResultBean getUserinfoById(@RequestParam("username") String username){
        return userService.getUserinfoByUsername(username);
    }

    @ApiOperation(value = "通过用户Token获取用户信息")
    @GetMapping("/getUserinfoByToken")
    public ResultBean getUserinfoByToken(@RequestParam("token") String token){
        String username = getUserByToken.getUserByToken(token).getUsername();
        return userService.getUserinfoByUsername(username);
    }

}
