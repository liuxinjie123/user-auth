package com.user.auth.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.user.auth.user.entity.RoleMenu;
import com.user.auth.user.mapper.RoleMenuMapper;
import com.user.auth.user.service.IRoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther jack
 * @create 2022-01-22 14:31:09
 * @describe 角色菜单关系表服务实现类
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {
    @Resource
    private RoleMenuMapper roleMenuMapper;

    @Override
    public List<RoleMenu> findByRoleId(String roleId) {
        return roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleId));
    }

    @Override
    public List<RoleMenu> findByRoleIdList(List<String> roleIdList) {
        return roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getRoleId, roleIdList));
    }

}
