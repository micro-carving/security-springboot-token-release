package com.security.token.exception;

/**
 * 权限认证异常
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/11/3
 */
public class AuthenticationException extends RuntimeException {
    private String message;

    public AuthenticationException(String message) {
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
