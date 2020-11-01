package com.security.token.constant;

import cn.hutool.http.HttpStatus;

import java.io.Serializable;

/**
 * TODO
 *
 * @author : TYKY_HTOJ
 * @version : v1.0
 * @since : 2020/10/31
 */
public final class StatusCodeConstant extends HttpStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    // --------- 参数错误状态码：1001-1999 ---------
    /**
     * 参数无效
     */
    public static final int PARAM_IS_INVALID = 1001;
    /**
     * 参数为空
     */
    public static final int PARAM_IS_NULL_OR_BLANK = 1002;
    /**
     * 参数类型错误
     */
    public static final int PARAM_TYPE_ERROR = 1003;
    /**
     * 参数缺失
     */
    public static final int PARAM_MISS = 1004;
    /**
     * 参数校验失败
     */
    public static final int PARAM_VALID_ERROR = 1005;

    // --------- 用户错误状态码：2001-2999 ---------
    /**
     * 用户未登录，需要验证，请登录
     */
    public static final int USER_NOT_LOGIN_IN = 2001;
    /**
     * 账号或密码错误
     */
    public static final int USER_LOGIN_ERROR = 2002;
    /**
     * 账号已被禁用
     */
    public static final int USER_ACCOUNT_FORBIDDEN = 2003;
    /**
     * 用户不存在
     */
    public static final int USER_NOT_EXIST = 2004;
    /**
     * 用户已存在
     */
    public static final int USER_HAS_EXISTED = 2005;

    // --------- 操作错误状态码：3001-3999 ---------
    /**
     * 未知异常
     */
    public static final int OPT_UNKNOWN_ERROR = 3001;
    /**
     * 添加失败
     */
    public static final int OPT_ADD_ERROR = 3002;
    /**
     * 更新失败
     */
    public static final int OPT_UPDATE_ERROR = 3003;
    /**
     * 删除失败
     */
    public static final int OPT_DELETE_ERROR = 3004;
    /**
     * 查找失败
     */
    public static final int OPT_SELECT_ERROR = 3005;
}
