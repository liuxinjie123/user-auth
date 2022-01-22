package com.user.auth.config;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.user.auth.annotation.CheckPermissions;
import com.user.auth.exception.BusinessException;
import com.user.auth.exception.NotLoginException;
import com.user.auth.user.service.IMenuService;
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

@Aspect
@Component
public class CheckPermissionsAspect {
    @Resource
    private IMenuService menuService;

    @Pointcut("@annotation(com.user.auth.annotation.CheckPermissions)")
    public void checkPermissions() {}

    @Before("checkPermissions()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取request
        String userId = String.valueOf(request.getAttribute("userId"));
        if (StringUtils.isBlank(userId)) {
            throw new NotLoginException("请登录");
        }

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
