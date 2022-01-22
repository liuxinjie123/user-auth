package com.user.auth.user.service;

import com.user.auth.user.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @auther jack
 * @create 2022-01-22 14:30:53
 * @describe 用户角色表服务类
 */
public interface IUserRoleService extends IService<UserRole> {

    void deleteByUserId(String userId);

    List<UserRole> findByUserId(String userId);

}
