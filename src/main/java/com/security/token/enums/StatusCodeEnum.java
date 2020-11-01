package com.security.token.enums;

import com.security.token.constant.StatusCodeConstant;
import lombok.Getter;

/**
 * 状态码枚举类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/31
 */
@Getter
public enum StatusCodeEnum {
    // --------- 重写状态码 ---------
    /**
     * 成功
     */
    SUCCESS(StatusCodeConstant.HTTP_OK, "操作成功！"),
    /**
     * 失败
     */
    FAILURE(StatusCodeConstant.HTTP_BAD_REQUEST, "操作失败！"),
    /**
     * 请求未授权
     */
    UN_AUTHORIZED(StatusCodeConstant.HTTP_UNAUTHORIZED, "请求未授权！"),
    /**
     * 请求未找到
     */
    NOT_FOUND(StatusCodeConstant.HTTP_NOT_FOUND, "404 请求未找到！"),
    /**
     * 不支持当前请求方法
     */
    METHOD_NOT_SUPPORTED(StatusCodeConstant.HTTP_BAD_METHOD, "不支持当前请求方法！"),
    /**
     * 不支持当前媒体类型
     */
    MEDIA_TYPE_NOT_SUPPORTED(StatusCodeConstant.HTTP_UNSUPPORTED_TYPE, "不支持当前媒体类型！"),
    /**
     * 请求被拒绝
     */
    REQ_REJECT(StatusCodeConstant.HTTP_NOT_ACCEPTABLE, "请求被拒绝！"),
    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(StatusCodeConstant.HTTP_INTERNAL_ERROR, "服务器异常！"),

    // --------- 参数错误状态码：1001-1999 ---------
    /**
     * 参数无效
     */
    PARAM_IS_INVALID(StatusCodeConstant.PARAM_IS_INVALID, "参数无效！"),
    /**
     * 参数为空
     */
    PARAM_IS_NULL_OR_BLANK(StatusCodeConstant.PARAM_IS_NULL_OR_BLANK, "参数为空！"),
    /**
     * 参数类型错误
     */
    PARAM_TYPE_ERROR(StatusCodeConstant.PARAM_TYPE_ERROR, "参数类型错误！"),
    /**
     * 参数缺失
     */
    PARAM_NOT_COMPLETE(StatusCodeConstant.PARAM_MISS, "参数缺失！"),
    /**
     * 参数校验失败
     */
    PARAM_VALID_ERROR(StatusCodeConstant.PARAM_VALID_ERROR, "参数校验失败！"),

    // --------- 用户错误状态码：2001-2999 ---------
    /**
     * 用户未登录，需要验证，请登录
     */
    USER_NOT_LOGIN_IN(StatusCodeConstant.USER_NOT_LOGIN_IN, "用户未登录，需要验证，请登录！"),
    /**
     * 账号或密码错误
     */
    USER_LOGIN_ERROR(StatusCodeConstant.USER_LOGIN_ERROR, "账号或密码错误！"),
    /**
     * 账号已被禁用
     */
    USER_ACCOUNT_FORBIDDEN(StatusCodeConstant.USER_ACCOUNT_FORBIDDEN, "账号已被禁用！"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(StatusCodeConstant.USER_NOT_EXIST, "用户不存在！"),
    /**
     * 用户已存在
     */
    USER_HAS_EXISTED(StatusCodeConstant.USER_HAS_EXISTED, "用户已存在！"),

    // --------- 操作错误状态码：3001-3999 ---------
    /**
     * 未知异常
     */
    OPT_UNKNOWN_ERROR(StatusCodeConstant.OPT_UNKNOWN_ERROR, "未知错误！"),
    /**
     * 添加失败
     */
    OPT_ADD_ERROR(StatusCodeConstant.OPT_ADD_ERROR, "添加失败！"),
    /**
     * 更新失败
     */
    OPT_UPDATE_ERROR(StatusCodeConstant.OPT_UPDATE_ERROR, "更新失败！"),
    /**
     * 删除失败
     */
    OPT_DELETE_ERROR(StatusCodeConstant.OPT_DELETE_ERROR, "删除失败！"),
    /**
     * 查找失败
     */
    OPT_SELECT_ERROR(StatusCodeConstant.OPT_SELECT_ERROR, "查询失败！")
    ;

    private final Integer code;
    private final String message;

    StatusCodeEnum(Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 通过状态码获取枚举对象
     *
     * @param code 状态码
     * @return 枚举对象
     */
    public static StatusCodeEnum getByCode(int code) {
        for (StatusCodeEnum statusCodeEnum : StatusCodeEnum.values()) {
            if (code == statusCodeEnum.getCode()) {
                return statusCodeEnum;
            }
        }
        return null;
    }
}
