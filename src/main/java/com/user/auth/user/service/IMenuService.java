package com.user.auth.user.service;

import com.user.auth.user.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @auther jack
 * @create 2022-01-22 14:30:39
 * @describe 菜单表服务类
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 校验用户是否有权限访问此接口
     * @param userId            用户id
     * @param menuCode          菜单code
     */
    int checkUserPermission(String userId, String menuCode);

    /**
     * 通过 menuId list 查询 menu 列表
     */
    List<Menu> findByMenuIdList(List<String> menuIdList);

    /**
     * 通过 parentId 查询 menu 列表
     */
    List<Menu> findByParentId(String parentId);

}
