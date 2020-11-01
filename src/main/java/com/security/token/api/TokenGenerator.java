package com.security.token.api;

import org.springframework.stereotype.Component;

/**
 * token生成器
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@Component
public interface TokenGenerator<T> {
    /**
     * 通过手动生成token字符串
     *
     * @param strings ：可变字符串参数
     * @return {String}
     */
    String getTokenByManual(String... strings);

    /**
     * 通过JWT生成token
     *
     * @param encryptData ：加密的数据对象
     * @return {String}
     */
    String getTokenByJwt(T encryptData);

    /**
     * 验证token的合法性
     *
     * @param token       : token字符串
     * @param decryptData ：解密数据
     * @return {true|false}
     */
    boolean verification(String token, T decryptData);
}
