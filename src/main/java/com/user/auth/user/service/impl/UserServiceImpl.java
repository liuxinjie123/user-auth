package com.user.auth.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.user.auth.common.dto.Result;
import com.user.auth.user.dto.LoginDto;
import com.user.auth.user.dto.UserDto;
import com.user.auth.user.entity.User;
import com.user.auth.user.mapper.UserMapper;
import com.user.auth.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @auther jack
 * @create 2022-01-22 14:27:47
 * @describe 用户表服务实现类
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User findByMobile(String mobile) {
        return userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile, mobile).last(" limit 1 "));
    }

    @Override
    public User checkLogin(LoginDto loginDto) {
        User user = findByMobile(loginDto.getMobile());
        if (null == user) {
            return null;
        }
        if (user.getPassword().equals(loginDto.getPassword())) {
            return user;
        }
        return null;
    }
}
