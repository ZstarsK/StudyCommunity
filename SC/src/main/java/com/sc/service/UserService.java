package com.sc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.sc.entity.User;
import com.sc.vo.ResultBean;
import com.sc.vo.param.UserInfoUpdateParam;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
@Service
public interface UserService extends IService<User> {

    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param request
     * @return
     */
    ResultBean login(String username, String password, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 用户注册
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */



    ResultBean register(String username, String name, String clazzId, String sex,
                        String role, String schoolId, String password, String avatar,
                        String cover, String code, HttpServletRequest request);

    /**
     * 用户上传头像
     * @param id
     * @param pathDB
     * @return
     */
    /**
     * 用户修改信息（除头像）
     * @param userInfoUpdateParam
     */
    ResultBean updateUserinfo(UserInfoUpdateParam userInfoUpdateParam);

    ResultBean getUserinfoById(String username);
    /**
     * 通过用户id获取用户信息
     * @param id
     * @return
     */
    User queryUserinfoById(Integer id);


    /**
     * 通过id批量查询用户信息
     * @param userId
     * @return
     */
    User getUserInfoByUserId(Integer userId);


}
