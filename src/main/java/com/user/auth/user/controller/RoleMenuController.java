package com.user.auth.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.user.auth.common.dto.Result;
import com.user.auth.user.dto.RoleMenuDto;
import com.user.auth.user.entity.Role;
import com.user.auth.user.entity.RoleMenu;
import com.user.auth.user.service.IRoleMenuService;
import com.user.auth.user.service.IRoleService;
import com.user.auth.user.service.impl.RoleServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther jack
 * @create 2022-01-22 14:31:09
 * @describe 角色菜单关系表前端控制器
 */
@RestController
@RequestMapping("/user/role-menu")
public class RoleMenuController {
    @Resource
    private IRoleMenuService roleMenuService;
    @Resource
    private IRoleService roleService;

    @ApiOperation(value = "新增、编辑角色菜单绑定关系")
    @PostMapping
    public Result add(@Validated @RequestBody RoleMenuDto roleMenuDto) {
        Role role = roleService.getById(roleMenuDto.getRoleId());
        if (null == role) {
            return Result.error("角色不存在");
        }
        /** 删除老的绑定关系 **/
        roleMenuService.remove(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleMenuDto.getRoleId()));
        /** 新增新的绑定关系 **/
        if (!CollectionUtils.isEmpty(roleMenuDto.getMenuIdList())) {
            List<RoleMenu> list = roleMenuDto.getMenuIdList().stream().map(menuId -> new RoleMenu().setRoleId(roleMenuDto.getRoleId()).setMenuId(menuId)).collect(Collectors.toList());
            roleMenuService.saveBatch(list);
        }
        return Result.success();
    }

}

