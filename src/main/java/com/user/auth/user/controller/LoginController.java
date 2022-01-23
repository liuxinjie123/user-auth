package com.user.auth.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.user.auth.common.dto.Result;
import com.user.auth.user.dto.LoginDto;
import com.user.auth.user.dto.MenuDto;
import com.user.auth.user.dto.UserDto;
import com.user.auth.user.entity.Menu;
import com.user.auth.user.entity.User;
import com.user.auth.user.service.IUserService;
import com.user.auth.util.RedisUtils;
import com.user.auth.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "登录相关接口")
@RestController
public class LoginController {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private IUserService userService;
    @Resource
    private RedisUtils redisUtils;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result<UserDto> login(@Validated @RequestBody LoginDto loginDto) {
        User user = userService.checkLogin(loginDto);
        if (null == user) {
            return Result.error("登陆失败，用户名或密码错误");
        }
        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);
        List<String> menuIdList = userService.findMenuIdListByUserId(user.getId());
        userDto.setMenuIdList(menuIdList);
        List<Menu> menuList = userService.findMenuListByUserId(user.getId());
        if (!CollectionUtils.isEmpty(menuList)) {
            List<String> menuCodeList = menuList.stream().map(Menu::getMenuCode).collect(Collectors.toList());
            userDto.setMenuCodeList(menuCodeList);
            List<MenuDto> menuDtoList = userService.packageMenuTree(menuList);
            userDto.setMenuList(menuDtoList);
        }
        String token = tokenUtil.getToken(userDto.getId(), userDto.getMobile());
        userDto.setToken(token);
        redisUtils.set(token, userDto);
        return Result.success(userDto);
    }

    @ApiOperation(value = "获取当前的录入user信息")
    @GetMapping("/self")
    public Result<UserDto> selfInfo(HttpServletRequest request) {
        String token = request.getHeader("token");
        UserDto userDto = (UserDto) redisUtils.get(token);
        return Result.success(userDto);
    }

    /**
     * 使用 /test-token 测试 token，进过拦截器
     */
    @RequestMapping("/test-token")
    public Result testToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        return Result.success(tokenUtil.parseToken(token));
    }

}