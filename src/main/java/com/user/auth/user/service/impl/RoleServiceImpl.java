package com.user.auth.user.service.impl;

import com.user.auth.user.entity.Role;
import com.user.auth.user.mapper.RoleMapper;
import com.user.auth.user.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @auther jack
 * @create 2022-01-22 14:29:50
 * @describe 角色表服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
