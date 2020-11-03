package com.security.token.handler;

import com.security.token.api.Result;
import com.security.token.exception.AuthenticationException;
import com.security.token.exception.TokenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局统一异常处理器
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/11/3
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 对token异常进行相关处理
     *
     * @param e ：token异常
     */
    @ExceptionHandler(TokenException.class)
    @ResponseBody
    public static Result<String> handleTokenException(TokenException e) {
        logger.error(" === token 发生异常 === ");
        logger.error(" 发生异常如下：---> {} ", e.getMessage());
        return Result.fail(e.getMessage());
    }

    /**
     * 对权限认证异常进行相关处理
     *
     * @param e ：权限认证异常
     */
    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public static Result<String> handleAuthenticationException(AuthenticationException e) {
        logger.error(" === 权限认证发生异常 === ");
        logger.error(" 发生异常如下：---> {}", e.getMessage());
        return Result.fail(e.getMessage());
    }
}
