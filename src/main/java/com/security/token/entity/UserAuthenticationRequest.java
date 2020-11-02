package com.security.token.entity;

import lombok.Data;

/**
 * 认证请求实体
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/11/3
 */
@Data
public class UserAuthenticationRequest {

    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
}
