package com.sc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.config.security.JwtTokenUtil;
import com.sc.mapper.UserMapper;
import com.sc.entity.User;
import com.sc.vo.ResultBean;
import com.sc.service.UserService;
import com.sc.vo.param.UserInfoUpdateParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import static com.sc.Util.DataUtil.*;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;



    @Override
    public ResultBean login(String username, String password, HttpServletRequest request) {
        User user = getUserByUsername(username);

        if (user != null && passwordEncoder.matches(password,user.getPassword())){

            return ResultBean.success("登录成功",generateTokenMap(username));

        }
        return ResultBean.error("用户名或密码不正确！");
    }


    @Override
    public User getUserByUsername(String username) {
        User user = null;
        if (StringUtils.isNoneBlank(username)){
            user = userMapper.selectOne(
                    getQueried(User.class,"username",username));
        }
        return user;
        // return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }



    @Override
    public ResultBean register(String username, String name, String clazzId, String sex,
                               String role, String schoolId, String password, String avatar,
                               String cover, String code, HttpServletRequest request) {
        if (StringUtils.isNoneBlank(code) && code.length() == 6){

            if (StringUtils.isNoneBlank(username) && StringUtils.isNoneBlank(password)){
                // 判断用户是否存在
                User user = userMapper.selectOne(
                        new LambdaQueryWrapper<User>()
                                .eq(User::getUsername, username)
                );
                if (user == null){
                    User newUser = new User();
                    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    newUser.setUsername(username)
                            .setName(name)
                            .setClazzId(clazzId)
                            .setSex(sex)
                            .setRole(role)
                            .setSchoolId(schoolId)
                            .setPassword(bCryptPasswordEncoder.encode(password))
                            .setAvatar(avatar)
                            .setCover(cover);

                    userMapper.insert(newUser);
                    return ResultBean.success("注册成功！",newUser);
                }
                return ResultBean.error("用户名已经存在！");
            }
            return ResultBean.error("用户名或密码不能为空！");
        }
        return ResultBean.error("验证码不符合规范！");
    }


    @Override
    public void updateAvatar(String username, String url) {
        this.update(getUpdated(User.class,"username",username,"avatar",url));
    }


    @Override
    public ResultBean updateUserinfo(UserInfoUpdateParam userInfoUpdateParam) {
        User user = new User();
        user.setName(userInfoUpdateParam.getName());

        this.updateById(user);

        return ResultBean.success("保存成功！");
    }

    @Override
    public User queryUserinfoById(Integer id) {

        return this.getById(id);
    }


    @Override
    public ResultBean getUserinfoByUsername(String username) {
        User user = userMapper.selectOne(getQueried(User.class,"username",username));
        user.setPassword(null);
        return ResultBean.success("用户信息获取成功",user);
    }



    @Override
    public User getUserInfoByUserId(Integer userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public HashMap<String, String> generateTokenMap(String username) {
        User user =  getUserByUsername(username);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        // 生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        tokenMap.put("name",user.getName());

        return tokenMap;
    }

}
