package com.security.token.api;

import com.security.token.enums.StatusCodeEnum;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * 统一返回结果泛型处理类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/11/1
 */
@Getter
public class Result<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;
    private String message;
    private T data;

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(StatusCodeEnum statusCodeEnum) {
        this(statusCodeEnum.getCode(), statusCodeEnum.getMessage(), null);
    }

    private Result(StatusCodeEnum statusCodeEnum, String message) {
        this(statusCodeEnum.getCode(), message, null);
    }

    private Result(StatusCodeEnum statusCodeEnum, T data) {
        this(statusCodeEnum.getCode(), statusCodeEnum.getMessage(), data);
    }

    private Result(StatusCodeEnum statusCodeEnum, String message, T data) {
        this(statusCodeEnum.getCode(), message, data);
    }

    public static <T> Result<T> data(T data) {
        return data(StatusCodeEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> data(String message, T data) {
        return data(StatusCodeEnum.SUCCESS.getCode(), message, data);
    }

    public static <T> Result<T> data(int code, String message, T data) {
        return new Result(code, data == null ? "暂无承载数据！" : message, data);
    }

    public static <T> Result<T> success(String message) {
        return new Result(StatusCodeEnum.SUCCESS, message);
    }

    public static <T> Result<T> success(StatusCodeEnum statusCodeEnum) {
        return new Result(statusCodeEnum);
    }

    public static <T> Result<T> success(StatusCodeEnum statusCodeEnum, String message) {
        return new Result(statusCodeEnum, message);
    }

    public static <T> Result<T> fail(String message) {
        return new Result(StatusCodeEnum.FAILURE, message);
    }

    public static <T> Result<T> fail(int code, String message) {
        return new Result(Objects.requireNonNull(StatusCodeEnum.getByCode(code)), null, message);
    }

    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum) {
        return new Result(statusCodeEnum);
    }

    public static <T> Result<T> fail(StatusCodeEnum statusCodeEnum, String message) {
        return new Result(statusCodeEnum, message);
    }

    public static <T> Result<T> status(boolean flag) {
        return flag ? success(StatusCodeEnum.SUCCESS) : fail(StatusCodeEnum.FAILURE);
    }
}
