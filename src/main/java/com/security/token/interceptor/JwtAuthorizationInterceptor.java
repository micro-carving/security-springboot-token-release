package com.security.token.interceptor;

import cn.hutool.core.util.StrUtil;
import com.security.token.annotation.AuthToken;
import com.security.token.constant.RedisConstant;
import com.security.token.constant.StatusCodeConstant;
import com.security.token.constant.TokenConstant;
import com.security.token.entity.User;
import com.security.token.service.IUserService;
import com.security.token.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * jwt权限验证拦截器
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@Slf4j
public class JwtAuthorizationInterceptor implements HandlerInterceptor {

    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private int port;

    @Resource
    IUserService userService;

    @Resource
    TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        // 如果打上了AuthToken注解则需要验证token
        if (method.getAnnotation(AuthToken.class) != null || handlerMethod.getBeanType().getAnnotation(AuthToken.class) != null) {
            // 获取请求头内容
            String token = request.getHeader(TokenConstant.ACCESS_TOKEN);
            log.info("Get token from request header is {} ", token);
            String userName = "";
            Jedis jedis = new Jedis(hostName, port);
            if (StrUtil.isNotBlank(token)) {
                userName = jedis.get(TokenConstant.USER_NAME);
                log.info("Get userName from Redis is {}", userName);
            }
            final User user = userService.getByUserName(userName);
            // 验证token的合法性，判断token中加密的用户名称与数据库存入的用户名称是否相同
            if (tokenUtil.verification(token, user)) {
                final String timeStamp = jedis.get(TokenConstant.TIME_STAMP);
                long tokenBirthTime = Long.parseLong(timeStamp);
                log.info("token Birth time is: {}", tokenBirthTime);
                Long diff = System.currentTimeMillis() - tokenBirthTime;
                log.info("token is exist : {} ms", diff);
                // 重新设置Redis中的token过期时间
                if (diff > RedisConstant.TOKEN_RESET_TIME) {
                    jedis.expire(TokenConstant.USER_NAME, RedisConstant.TOKEN_EXPIRE_TIME);
                    jedis.expire(TokenConstant.ACCESS_TOKEN, RedisConstant.TOKEN_EXPIRE_TIME);
                    long newBirthTime = System.currentTimeMillis();
                    jedis.set(TokenConstant.TIME_STAMP, Long.toString(newBirthTime));
                    jedis.expire(TokenConstant.TIME_STAMP, RedisConstant.TOKEN_EXPIRE_TIME);
                    log.info("Reset expire time success!");
                }
                final String authorities = user.getAuthorities();
                // 请求的url
                final String requestUri = request.getRequestURI();
                // 具有p1权限才能访问r1上的资源，具有p2权限才能访问r2上的资源，以此类推！
                if (authorities.contains("p1") && requestUri.contains("/r/r1")) {
                    return true;
                } else if (authorities.contains("p2") && requestUri.contains("/r/r2")) {
                    return true;
                } else if (authorities.contains("p3") && requestUri.contains("/r/r3")) {
                    return true;
                } else {
                    this.responseContent(response, "没有权限，拒绝访问！", StatusCodeConstant.HTTP_UNAUTHORIZED);
                }
                // 用完关闭
                jedis.close();
                return true;
            } else {
                this.responseContent(response, "token 验证不合法！", StatusCodeConstant.PARAM_IS_INVALID);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    /**
     * 响应输出内容
     *
     * @param response   ：响应
     * @param msg        ：响应信息
     * @param statusCode ：响应状态码
     * @throws IOException ：io异常
     */
    private void responseContent(HttpServletResponse response, String msg, int statusCode) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(statusCode);
        final PrintWriter writer = response.getWriter();
        writer.println(msg);
        writer.close();
    }
}
