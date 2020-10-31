package com.security.token.api;

import java.io.Serializable;

/**
 * 状态码值
 *
 * @author : TYKY_HTOJ
 * @version : v1.0
 * @since : 2020/10/31
 */
public interface IResultCode extends Serializable {

    /**
     * 状态码
     *
     * @return {int}
     */
    int getCode();

    /**
     * 获取状态码信息描述
     *
     * @return {String}
     */
    String getMessage();
}
