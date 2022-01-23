package com.user.auth.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.user.auth.user.entity.Menu;
import com.user.auth.user.entity.RoleMenu;
import com.user.auth.user.entity.UserRole;
import com.user.auth.user.mapper.MenuMapper;
import com.user.auth.user.mapper.UserRoleMapper;
import com.user.auth.user.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.user.auth.user.service.IRoleMenuService;
import com.user.auth.user.service.IUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther jack
 * @create 2022-01-22 14:30:39
 * @describe 菜单表服务实现类
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    private MenuMapper menuMapper;

    @Override
    public int checkUserPermission(String userId, String menuCode) {
        List<UserRole> userRoleList = userRoleService.findByUserId(userId);
        if (CollectionUtils.isEmpty(userRoleList)) {
            return 0;
        }
        List<String> roleIdList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<RoleMenu> roleMenuList = roleMenuService.findByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(roleMenuList)) {
            return 0;
        }
        List<String> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        Menu menu = menuMapper.selectOne(new QueryWrapper<Menu>().lambda().eq(Menu::getMenuCode, menuCode).last(" limit 1 "));
        if (null == menu) {
            return 0;
        }
        if (menuIdList.contains(menu.getId())) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<Menu> findByMenuIdList(List<String> menuIdList) {
        return menuMapper.selectList(new QueryWrapper<Menu>().lambda().in(Menu::getId, menuIdList).eq(Menu::getDel, 0));
    }

    @Override
    public List<Menu> findByParentId(String parentId) {
        return menuMapper.selectList(new QueryWrapper<Menu>().lambda().eq(Menu::getParentId, parentId).eq(Menu::getDel, 0));
    }
}
