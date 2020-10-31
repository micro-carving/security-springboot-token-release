package com.security.token.interceptor;

import com.security.token.annotation.ResponseResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 响应结果拦截器，
 *
 * @author : TYKY_HTOJ
 * @version : v1.0
 * @since : 2020/10/31
 */
public class ResponseResultInterceptor implements HandlerInterceptor {

    /**
     * 请求返回体属性标记名称
     */
    public static final String RESPONSE_RESULT_ANNOTATION = "RESPONSE_RESULT_ANNOTATION";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 请求的方法
        if (handler instanceof HandlerMethod) {
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> beanTypeClass = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            // 判断是否在类对象上加了ResponseResult注解
            if (beanTypeClass.isAnnotationPresent(ResponseResult.class)) {
                // 设置此请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口中进行判断
                request.setAttribute(RESPONSE_RESULT_ANNOTATION, beanTypeClass.getAnnotation(ResponseResult.class));
            }
            // 判断是否在方法上加了ResponseResult注解
            if (method.isAnnotationPresent(ResponseResult.class)) {
                // 设置此请求返回体，需要包装，往下传递，在ResponseBodyAdvice接口中进行判断
                request.setAttribute(RESPONSE_RESULT_ANNOTATION, method.getAnnotation(ResponseResult.class));
            }
            return true;
        }
        return false;
    }
}
