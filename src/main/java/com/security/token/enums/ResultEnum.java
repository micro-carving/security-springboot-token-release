package com.security.token.enums;

import cn.hutool.http.HttpStatus;

/**
 * TODO
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/31
 */
public enum ResultEnum {
    // --------- 成功状态码 ---------
    /**
     * 成功
     */
    SUCCESS(1, "成功！"),

    // --------- 参数错误状态码：1001-1999 ---------
    /**
     * 参数无效
     */
    PARAM_IS_INVALID(1001, "参数无效！"),
    /**
     * 参数为空
     */
    PARAM_IS_NULL_OR_BLANK(1002, "参数为空！"),
    /**
     * 参数类型错误
     */
    PARAM_TYPE_ERROR(1001, "参数类型错误！"),
    /**
     * 参数缺失
     */
    PARAM_NOT_COMPLETE(1001, "参数缺失！"),

    // --------- 用户错误状态码：2001-2999 ---------
    /**
     * 用户未登录，需要验证，请登录
     */
    USER_NOT_LOGIN_IN(2001, "用户未登录，需要验证，请登录！"),
    /**
     * 账号或密码错误
     */
    USER_LOGIN_ERROR(2002,"账号或密码错误！"),
    /**
     * 账号已被禁用
     */
    USER_ACCOUNT_FORBIDDEN(2003, "账号已被禁用！"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(2004, "用户不存在！"),
    /**
     * 用户已存在
     */
    USER_HAS_EXISTED(2005, "用户已存在！"),

    // --------- 操作错误状态码：3001-3999 ---------
    /**
     * 未知异常
     */
    OPT_UNKNOWN_ERROR(3001, "未知错误！"),
    /**
     * 添加失败
     */
    OPT_ADD_ERROR(3002, "添加失败！"),
    /**
     * 更新失败
     */
    OPT_UPDATE_ERROR(3003, "更新失败！"),
    /**
     * 删除失败
     */
    OPT_DELETE_ERROR(3004, "删除失败！"),
    /**
     * 查找失败
     */
    OPT_GET_ERROR(3005, "查找失败！"),
    ;
    private final Integer code;
    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    /**
     * 通过状态码获取枚举对象
     *
     * @param code 状态码
     * @return 枚举对象
     */
    public static ResultEnum getByCode(int code) {
        for (ResultEnum resultEnum : ResultEnum.values()) {
            if (code == resultEnum.getCode()) {
                return resultEnum;
            }
        }
        return null;
    }
}
