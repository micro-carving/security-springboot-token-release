package com.security.token.config;

import com.security.token.interceptor.JwtAuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器相关配置
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    JwtAuthorizationInterceptor jwtAuthorizationInterceptor;

    /**
     * 为视图添加拦截器，拦截所有请求
     *
     * @param registry ：视图拦截器注册中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthorizationInterceptor).addPathPatterns("/r/**");
    }

    /**
     * 为视图添加跨域映射
     *
     * @param registry ：视图拦截器注册中心
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOrigins("*")
                // 设置允许跨域请求头
                .allowedHeaders("*")
                // 是否允许证书，默认关闭
                //.allowCredentials(true)
                // 设置允许访问方法
                .allowedMethods("GET", "POST")
                // 跨域允许时间
                .maxAge(3600);
    }

    /**
     * 此方式是将拦截器对象注入进来，不需要在来拦截器上配置@Component注解
     *
     * @return JwtAuthorizationInterceptor
     */
    @Bean
    public JwtAuthorizationInterceptor jwtAuthorizationInterceptor() {
        return new JwtAuthorizationInterceptor();
    }
}
