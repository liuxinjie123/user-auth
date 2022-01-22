package com.user.auth.user.service;

import com.user.auth.user.entity.RoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @auther jack
 * @create 2022-01-22 14:31:09
 * @describe 角色菜单关系表服务类
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    /**
     * 通过 角色id 查询菜单列表
     * @param roleId       角色id
     */
    List<RoleMenu> findByRoleId(String roleId);

    /**
     * 通过 角色id list 查询菜单列表
     * @param roleIdList    角色id list
     */
    List<RoleMenu> findByRoleIdList(List<String> roleIdList);
}
