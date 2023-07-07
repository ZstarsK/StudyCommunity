package com.sc.controller;


import com.sc.mapper.PostMapper;
import com.sc.service.PostService;
import com.sc.service.UserService;
import com.sc.vo.ResultBean;
import com.sc.vo.param.UserRegisterParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import static com.sc.Util.DataUtil.*;

/**
 * 注册
 */

@Api(tags = "RegisterController")
@RestController
public class RegisterController {
    @Value("${avatar_storage.path}")
    private String avtPath;
    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;


    @ApiOperation(value = "注册")
    @PostMapping("/common/register")
    public ResultBean register(@RequestBody UserRegisterParam userRegisterParam, HttpServletRequest request){
        if (userRegisterParam != null){
            return userService.register(userRegisterParam.getUsername(),
                    userRegisterParam.getName(),
                    userRegisterParam.getClazzId(),
                    userRegisterParam.getSex(),
                    userRegisterParam.getRole(),
                    userRegisterParam.getSchoolId(),
                    userRegisterParam.getPassword(),
                    userRegisterParam.getAvatar(),
                    userRegisterParam.getCover(),
                    userRegisterParam.getCode(),request);
        }
        return ResultBean.error("用户名密码不能为空！");
    }
    @ApiOperation(value = "上传注册图片")
    @PostMapping("/common/register/upload")
    public ResultBean registerUpload(@RequestParam("username") String username,
                                     @RequestParam("file") MultipartFile file) throws IOException {

        userService.updateAvatar(username,saveFiles(file,avtPath,username));

        return ResultBean.success("头像上传成功");

    }
    //postService.saveFile(file,avtPath,username)
}
