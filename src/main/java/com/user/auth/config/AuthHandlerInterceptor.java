package com.user.auth.config;

import com.user.auth.exception.NotLoginException;
import com.user.auth.exception.TokenAuthExpiredException;
import com.user.auth.user.service.IMenuService;
import com.user.auth.util.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Resource
    TokenUtil tokenUtil;
    @Value("${token.privateKey}")
    private String privateKey;
    @Value("${token.yangToken}")
    private Long yangToken;
    @Value("${token.oldToken}")
    private Long oldToken;

    /**
     * 权限认证的拦截操作.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        log.info("=======进入拦截器========");
        // 如果不是映射到方法直接通过,可以访问资源.
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        // 为空就返回错误
        String token = request.getHeader("token");
        if (null == token || "".equals(token.trim())) {
            throw new NotLoginException("请登录");
        }
        log.info("==============token:" + token);
        Map<String, String> map = tokenUtil.parseToken(token);
        String userId = map.get("userId");
        String userMobile = map.get("userMobile");
        long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));
        //1.判断 token 是否过期
        //年轻 token
        if (timeOfUse < yangToken) {
            log.info("年轻 token");
        } else if (timeOfUse >= yangToken && timeOfUse < oldToken) {
            //老年 token 就刷新 token
            response.setHeader("token", tokenUtil.getToken(userId, userMobile));
        } else {
            //过期 token 就返回 token 无效.
            throw new TokenAuthExpiredException("token无效或过期");
        }
        //2.角色匹配. TODO
        if ("user".equals(userMobile)) {
            log.info("========user账户============");
            return true;
        }
        if ("admin".equals(userMobile)) {
            log.info("========admin账户============");
            return true;
        }
        // TODO
        if (true) {
            request.setAttribute("userId", userId);
            return true;
        }
        return false;
    }

}

