package com.user.auth.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.user.auth.annotation.CheckPermissions;
import com.user.auth.common.dto.Result;
import com.user.auth.user.dto.MenuDto;
import com.user.auth.user.entity.Menu;
import com.user.auth.user.entity.RoleMenu;
import com.user.auth.user.service.IMenuService;
import com.user.auth.user.service.IRoleMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther jack
 * @create 2022-01-22 14:30:39
 * @describe 菜单表前端控制器
 */
@RestController
@RequestMapping("/user/menu")
public class MenuController {
    @Resource
    private IMenuService menuService;
    @Resource
    private IRoleMenuService roleMenuService;

    @CheckPermissions(value = "menuCenter:add")
    @PostMapping
    @ApiOperation(value = "新增、编辑菜单")
    public Result add(@RequestBody MenuDto menu) {
        if(menu.getParentId().equals("0")){
            menu.setLevel(1);//根节点层级为1
            menu.setPath(null);//根节点路径为空
        } else {
            Menu parentMenu = menuService.getById(menu.getParentId());
            if (StringUtils.isBlank(parentMenu.getPath())) {
                menu.setPath(parentMenu.getId());
            } else {
                menu.setPath(parentMenu.getPath() + "," + parentMenu.getId());
            }
        }
        menuService.saveOrUpdate(menu);
        return Result.success();
    }

    @CheckPermissions(value = "menuCenter:detail")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据 id 查询菜单详情")
    public Result getById(@PathVariable("id") String id) {
        Menu menu = menuService.getById(id);
        if (null == menu) {
            return Result.error("菜单不存在");
        }
        return Result.success(menu);
    }

    @CheckPermissions(value = "menuCenter:list")
    @GetMapping
    @ApiOperation(value = "菜单列表")
    public Result list(Menu menu) {
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().eq(Menu::getDel, 0);
        if (StringUtils.isNotBlank(menu.getId())) {
            query.lambda().eq(Menu::getId, menu.getId());
        }
        if (StringUtils.isNotBlank(menu.getName())) {
            query.lambda().like(Menu::getName, menu.getName());
        }
        List<Menu> list = menuService.list(query);
        return Result.success(list);
    }

    @GetMapping(value = "/role/{roleId}")
    @ApiOperation(value = "通过 角色id 查询菜单列表")
    public Result queryByRoleId(@PathVariable("roleId") String roleId) {
        List<RoleMenu> roleMenuList = roleMenuService.list(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleId));
        if (CollectionUtils.isEmpty(roleMenuList)) {
            return Result.success(new ArrayList<>());
        }
        List<String> menuIdList = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().eq(Menu::getDel, 0).in(Menu::getId, menuIdList).orderByAsc(Menu::getSort);
        List<Menu> list = menuService.list(query);
        return Result.success(list);
    }

    @CheckPermissions(value = "menuCenter:del")
    @PostMapping(value = "/del/{id}")
    @ApiOperation(value = "删除菜单")
    public Result del(@PathVariable("id") String id) {
        Menu menu = menuService.getById(id);
        if (null == menu) {
            return Result.error("菜单不存在");
        }
        menu.setDel(1);
        menuService.updateById(menu);
        return Result.success();
    }

}

