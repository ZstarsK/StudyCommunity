package com.sc.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ckgl.entity.User;
import com.ckgl.mapper.UserMapper;
import com.ckgl.service.UserService;
import org.springframework.stereotype.Service;
//test
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
