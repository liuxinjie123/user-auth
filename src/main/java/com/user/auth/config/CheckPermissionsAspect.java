package com.user.auth.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.user.auth.annotation.CheckPermissions;
import com.user.auth.exception.BusinessException;
import com.user.auth.exception.NotLoginException;
import com.user.auth.user.dto.UserDto;
import com.user.auth.user.service.IMenuService;
import com.user.auth.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class CheckPermissionsAspect {
    @Resource
    private IMenuService menuService;
    @Resource
    private RedisUtils redisUtils;

    @Pointcut("@annotation(com.user.auth.annotation.CheckPermissions)")
    public void checkPermissions() {}

    @Before("checkPermissions()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        /** 获取 request **/
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        /** 获取 response **/
//        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        // 判断登录
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new NotLoginException("请登录");
        }

        UserDto userDto = (UserDto) redisUtils.get(token);
        if (null == userDto) {
            throw new NotLoginException("请登录");
        }
        String userId = userDto.getId();
        /** 获取方法上有CheckPermissions注解的参数 **/
        Class clazz = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Class[] parameterTypes = ((MethodSignature) joinPoint.getSignature()).getMethod().getParameterTypes();
        Method method = clazz.getMethod(methodName, parameterTypes);
        if (method.getAnnotation(CheckPermissions.class) != null) {
            CheckPermissions annotation = method.getAnnotation(CheckPermissions.class);
            String menuCode = annotation.value();
            if (StringUtils.isNotBlank(menuCode)) {
                /** 通过用户ID、菜单编码查询是否有关联 **/
                int count = menuService.checkUserPermission(userId, menuCode);
                if (count == 0) {
                    throw new BusinessException("接口无访问权限");
                }
            }
        }
    }


}
