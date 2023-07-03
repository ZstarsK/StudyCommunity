package com.sc.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sc.entity.User;
import com.sc.mapper.UserMapper;
import com.sc.service.UserService;
import org.springframework.stereotype.Service;
//test
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
