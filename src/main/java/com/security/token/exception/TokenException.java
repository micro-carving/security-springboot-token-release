package com.security.token.exception;

/**
 * token异常
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/11/3
 */
public class TokenException extends RuntimeException {
    private String message;

    public TokenException(String message) {
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
