package com.user.auth.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.user.auth.annotation.CheckPermissions;
import com.user.auth.common.dto.Result;
import com.user.auth.user.dto.UserDto;
import com.user.auth.user.entity.User;
import com.user.auth.user.service.IUserService;
import com.user.auth.user.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @auther jack
 * @create 2022-01-22 14:27:47
 * @describe 用户表前端控制器
 */
@Api(value = "用户api")
@RestController
@RequestMapping("/user/user")
public class UserController {
    @Resource
    private IUserService userService;

    @CheckPermissions(value = "userCenter:add")
    @PostMapping
    @ApiOperation(value = "新增、编辑用户")
    public Result add(@RequestBody User user) {
        if (null != userService.findByMobile(user.getMobile())) {
            return Result.error("手机号已存在");
        }
        userService.saveOrUpdate(user);
        return Result.success();
    }

    @CheckPermissions(value = "userCenter:detail")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "根据 id 查询用户详情")
    public Result getById(@PathVariable("id") String id) {
        User user = userService.getById(id);
        if (null == user) {
            return Result.error("用户不存在");
        }
        return Result.success(BeanUtil.copyProperties(user, UserVO.class));
    }

    @CheckPermissions(value = "userCenter:list")
    @GetMapping
    @ApiOperation(value = "用户列表")
    public Result list(UserDto user) {
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getDel, 0);
        if (StringUtils.isNotBlank(user.getId())) {
            query.lambda().eq(User::getId, user.getId());
        }
        if (StringUtils.isNotBlank(user.getMobile())) {
            query.lambda().like(User::getMobile, user.getMobile());
        }
        if (StringUtils.isNotBlank(user.getName())) {
            query.lambda().like(User::getName, user.getName());
        }
        if (StringUtils.isNotBlank(user.getUsername())) {
            query.lambda().like(User::getUsername, user.getUsername());
        }
        List<User> list = userService.list(query);
        return Result.success(BeanUtil.copyToList(list, UserVO.class));
    }

    @CheckPermissions(value = "userCenter:del")
    @PostMapping(value = "/del/{id}")
    @ApiOperation(value = "删除用户")
    public Result del(@PathVariable("id") String id) {
        User user = userService.getById(id);
        if (null == user) {
            return Result.error("用户不存在");
        }
        user.setDel(1);
        userService.updateById(user);
        return Result.success();
    }


}

