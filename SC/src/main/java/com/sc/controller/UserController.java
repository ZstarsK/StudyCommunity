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

    @Value("${portrait_storage.ip}")
    private String ip;

    @Value("${portrait_storage.port}")
    private String port;

    @Value("${portrait_storage.path}")
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


    @ApiOperation(value = "修改当前用户头像")
    @PutMapping("/user/updatePortrait")
    public ResultBean updatePortrait(@RequestParam("userId") Integer id,@RequestParam("file") MultipartFile file) throws Exception{

        String pType = file.getContentType();
        pType = pType.substring(pType.indexOf("/") + 1);
        if ("jpeg".equals(pType)){
            pType = "jpg";
        }

        Long time = System.currentTimeMillis();

        // 文件保存的路径
        String path = porPath +"User/"+"id_"+id+"/portrait/"+time+"_."+pType;
//        System.out.println(path);

        File outFile = new File(path);
        if(outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()){
            // 创建文件夹
            outFile.getParentFile().mkdirs();
        }
        try {
            // 将前端传递的文件保存到本地服务器路径下
            file.transferTo(new File(path));

            // 同步到数据库的路径
            String pathDB = "http://" + ip + ":" + port + "/"+"User/"+"id_"+id+"/portrait/"+time+"_."+pType;
//            System.out.println(pathDB);
            return userService.updatePortrait(id,pathDB);

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @ApiOperation(value = "修改当前用户信息")
    @PutMapping("/user/updateUserinfo")
    public ResultBean updateUserinfo(@RequestBody UserInfoUpdateParam userInfoUpdateParam, HttpServletRequest request){

        return userService.updateUserinfo(userInfoUpdateParam);
    }

    @ApiOperation(value = "通过用户ID获取用户信息")
    @GetMapping("/getUserinfoById")
    public ResultBean getUserinfoById(@RequestParam("username") String username){
        return userService.getUserinfoById(username);
    }

    @ApiOperation(value = "通过用户Token获取用户信息")
    @GetMapping("/getUserinfoByToken")
    public ResultBean getUserinfoByToken(@RequestParam("token") String token){
        String username = getUserByToken.getUserByToken(token).getUsername();
        return userService.getUserinfoById(username);
    }

}
