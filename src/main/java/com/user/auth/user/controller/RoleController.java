package com.user.auth.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.user.auth.common.dto.Result;
import com.user.auth.user.entity.Role;
import com.user.auth.user.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther jack
 * @create 2022-01-22 14:29:50
 * @describe 角色表前端控制器
 */
@RestController
@RequestMapping("/user/role")
public class RoleController {
    @Resource
    private IRoleService roleService;

    @PostMapping
    @ApiOperation(value = "新增、编辑角色")
    public Result add(@RequestBody Role role) {
        roleService.saveOrUpdate(role);
        return Result.success();
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据 id 查询角色详情")
    public Result getById(@PathVariable("id") String id) {
        Role role = roleService.getById(id);
        if (null == role) {
            return Result.error("角色不存在");
        }
        return Result.success(role);
    }

    @GetMapping
    @ApiOperation(value = "角色列表")
    public Result list(Role role) {
        QueryWrapper<Role> query = new QueryWrapper<>();
        query.lambda().eq(Role::getDel, 0);
        if (StringUtils.isNotBlank(role.getId())) {
            query.lambda().eq(Role::getId, role.getId());
        }
        if (StringUtils.isNotBlank(role.getName())) {
            query.lambda().like(Role::getName, role.getName());
        }
        List<Role> list = roleService.list(query);
        return Result.success(list);
    }

    @PostMapping(value = "/del/{id}")
    @ApiOperation(value = "删除角色")
    public Result del(@PathVariable("id") String id) {
        Role role = roleService.getById(id);
        if (null == role) {
            return Result.error("角色不存在");
        }
        role.setDel(1);
        roleService.updateById(role);
        return Result.success();
    }
}

