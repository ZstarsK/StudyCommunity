package com.sc.Util;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sc.config.security.JwtTokenUtil;
import com.sc.entity.User;
import com.sc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetUserByToken {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    public User getUserByToken(String token) {
        token = token.substring(6);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );
    }
}
