package com.security.token.utils;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.security.token.api.TokenGenerator;
import com.security.token.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Map;

/**
 * Token工具类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/11/1
 */
@Component
public class TokenUtil implements TokenGenerator<User> {
    /**
     * 过期时间为15分钟
     */
    private static final long EXPIRE_TIME = 15 * 60 * 1000;
    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = IdUtil.simpleUUID();
    /**
     * 时间戳
     */
    private final long TIME_STAMP = System.currentTimeMillis();


    /**
     * 通过手动生成token字符串
     *
     * @param strings ：可变字符串参数
     * @return {String}
     */
    @Override
    public String getTokenByManual(String... strings) {
        StringBuilder tokenMeta = new StringBuilder(32);
        for (String s : strings) {
            tokenMeta.append(s).append(TOKEN_SECRET);
        }
        tokenMeta.append(TIME_STAMP);
        // 对字符串进行md5加密
        return DigestUtils.md5DigestAsHex(tokenMeta.toString().getBytes());
    }

    /**
     * 通过JWT生成token
     *
     * @param encryptData ：加密的数据对象
     * @return {String}
     */
    @Override
    public String getTokenByJwt(User encryptData) {
        String token;
        // 过期时间
        final Date date = new Date(TIME_STAMP + EXPIRE_TIME);
        // 私钥及加密算法
        final Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // 设置头部信息
        final Map<String, Object> header = MapUtil.newHashMap();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        // 附带用户名、手机号、权限等信息，生成签名
        token = JWT.create()
                .withHeader(header)
                .withClaim("userName", encryptData.getUserName())
                .withClaim("mobile", encryptData.getMobile())
                .withClaim("authorities", encryptData.getAuthorities())
                .withExpiresAt(date)
                .sign(algorithm);
        return token;
    }

    /**
     * 验证token的合法性，主要是通过判断token中加密的用户名称与数据库存入的用户名称是否相同
     *
     * @param token       : token字符串
     * @param decryptData ：解密数据
     * @return {true|false}
     */
    @Override
    public boolean verification(String token, User decryptData) {
        // 解密中所使用的算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // jwt验证器
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        // 验证token
        final DecodedJWT decodedJwt = jwtVerifier.verify(token);
        // 获取解密之后的数据
        final Claim userName = decodedJwt.getClaim("userName");
        // 判断token中加密的用户名称与数据库存入的用户名称是否相同
        return userName.asString().equals(decryptData.getUserName());
    }
}
