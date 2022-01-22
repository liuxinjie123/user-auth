package com.user.auth.user.service;

import com.user.auth.user.dto.LoginDto;
import com.user.auth.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @auther jack
 * @create 2022-01-22 14:27:47
 * @describe 用户表服务类
 */
public interface IUserService extends IService<User> {
    /**
     * 通过手机号查询用户
     * @param mobile         手机号
     */
    User findByMobile(String mobile);

    /**
     * 登录
     */
    User checkLogin(LoginDto loginDto);
}
