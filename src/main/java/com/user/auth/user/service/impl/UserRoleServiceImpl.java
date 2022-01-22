package com.user.auth.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.user.auth.user.entity.UserRole;
import com.user.auth.user.mapper.UserRoleMapper;
import com.user.auth.user.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther jack
 * @create 2022-01-22 14:30:53
 * @describe 用户角色表服务实现类
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public void deleteByUserId(String userId) {
        userRoleMapper.delete(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, userId));
    }

    @Override
    public List<UserRole> findByUserId(String userId) {
        return userRoleMapper.selectList(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, userId));
    }

}
