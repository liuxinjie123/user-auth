package com.user.auth.user.controller;

import cn.hutool.core.bean.BeanUtil;
import com.user.auth.common.dto.CommonConstants;
import com.user.auth.common.dto.Result;
import com.user.auth.user.dto.LoginDto;
import com.user.auth.user.dto.UserDto;
import com.user.auth.user.entity.User;
import com.user.auth.user.service.IUserService;
import com.user.auth.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(tags = "登录相关接口")
@RestController
public class LoginController {
    @Resource
    private TokenUtil tokenUtil;
    @Resource
    private IUserService userService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public Result<UserDto> login(@Validated @RequestBody LoginDto loginDto) {
        User user = userService.checkLogin(loginDto);
        if (null == user) {
            return Result.error("登陆失败，用户名或密码错误");
        }
        UserDto userDto = BeanUtil.copyProperties(user, UserDto.class);
        userDto.setToken(tokenUtil.getToken(userDto.getId(), userDto.getMobile()));
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