package com.user.auth.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.stream.CollectorUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.user.auth.user.dto.LoginDto;
import com.user.auth.user.dto.MenuDto;
import com.user.auth.user.entity.*;
import com.user.auth.user.mapper.UserMapper;
import com.user.auth.user.service.IMenuService;
import com.user.auth.user.service.IRoleMenuService;
import com.user.auth.user.service.IUserRoleService;
import com.user.auth.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.user.auth.util.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    private IMenuService menuService;
    @Resource
    private RedisUtils redisUtils;

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

    @Override
    public List<String> findMenuIdListByUserId(String userId) {
        List<RoleMenu> roleMenuList = findRoleMenuListByUserId(userId);
        if (CollectionUtils.isEmpty(roleMenuList)) {
            return new ArrayList<>();
        }
        // 菜单 id list
        List<String> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return menuIdList;
    }

    @Override
    public List<Menu> findMenuListByUserId(String userId) {
        List<RoleMenu> roleMenuList = findRoleMenuListByUserId(userId);
        if (CollectionUtils.isEmpty(roleMenuList)) {
            return new ArrayList<>();
        }
        // 菜单 id list
        List<String> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        // 菜单 list
        List<Menu> menuList = menuService.findByMenuIdList(menuIdList);
        return menuList;
    }

    @Override
    public List<MenuDto> packageMenuTree(List<Menu> menuList) {
        // 菜单 list
        Map<String, List<Menu>> menuMap = menuList.stream().collect(Collectors.groupingBy(Menu::getParentId));
        List<Menu> menus = menuMap.get("1");
        if (CollectionUtils.isEmpty(menus)) {
            return new ArrayList<>();
        }
        List<MenuDto> menuDtoList = BeanUtil.copyToList(menus, MenuDto.class);
        packageMenuList(menuDtoList, menuMap);
        return menuDtoList;
    }

    void packageMenuList(List<MenuDto> menuDtoList, Map<String, List<Menu>> menuMap) {
        for (MenuDto menu : menuDtoList) {
            List<MenuDto> subMenuList = BeanUtil.copyToList(menuMap.get(menu.getId()), MenuDto.class);
            if (CollectionUtils.isEmpty(subMenuList)) {
                continue;
            }
            packageMenuList(subMenuList, menuMap);
            menu.setMenuList(subMenuList);
        }
    }

    private List<RoleMenu> findRoleMenuListByUserId(String userId) {
        // 查询 用户-角色
        List<UserRole> roleList = userRoleService.findByUserId(userId);
        if (CollectionUtils.isEmpty(roleList)) {
            return new ArrayList<>();
        }
        // 角色 id list
        List<String> roleIdList = roleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 角色-菜单 list
        List<RoleMenu> roleMenuList = roleMenuService.findByRoleIdList(roleIdList);
        return roleMenuList;
    }

}
