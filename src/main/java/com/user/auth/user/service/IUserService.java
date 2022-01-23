package com.user.auth.user.service;

import com.user.auth.user.dto.LoginDto;
import com.user.auth.user.dto.MenuDto;
import com.user.auth.user.entity.Menu;
import com.user.auth.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    /**
     * 通过 userId 查找 menuId list
     */
    List<String> findMenuIdListByUserId(String userId);

    /**
     * 通过 userId 查找 menuCode list
     */
    List<Menu> findMenuListByUserId(String userId);

    /**
     * 封装 menuList 成 树形结构
     */
    List<MenuDto> packageMenuTree(List<Menu> menuList);

}
