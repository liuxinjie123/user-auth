package com.user.auth.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.user.auth.common.dto.Result;
import com.user.auth.user.dto.UserRoleDto;
import com.user.auth.user.entity.User;
import com.user.auth.user.entity.UserRole;
import com.user.auth.user.service.IUserRoleService;
import com.user.auth.user.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther jack
 * @create 2022-01-22 14:30:53
 * @describe 用户角色表前端控制器
 */
@RestController
@RequestMapping("/user/user-role")
public class UserRoleController {
    @Resource
    private IUserRoleService userRoleService;
    @Resource
    private IUserService userService;

    @ApiOperation(value = "新增、编辑用户角色绑定关系")
    @PostMapping
    public Result add(@Validated @RequestBody UserRoleDto userRoleDto) {
        User user = userService.getById(userRoleDto.getUserId());
        if (null == user) {
            return Result.error("用户不存在");
        }
        /** 删除现有的 用户-角色 绑定关系 **/
        userRoleService.remove(new QueryWrapper<UserRole>().lambda().eq(UserRole::getUserId, userRoleDto.getUserId()));
        /** 新增 用户-角色 绑定关系 **/
        if (!CollectionUtils.isEmpty(userRoleDto.getRoleIdList())) {
            List<UserRole> userRoleList = userRoleDto.getRoleIdList().stream().map(roleId -> new UserRole().setRoleId(roleId).setUserId(userRoleDto.getUserId())).collect(Collectors.toList());
            userRoleService.saveBatch(userRoleList);
        }
        return Result.success();
    }

}

