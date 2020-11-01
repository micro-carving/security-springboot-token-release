package com.security.token.utils;

import com.security.token.constant.RedisConstant;
import com.security.token.constant.TokenConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Redis工具类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/11/1
 */
@Component
public class RedisUtil {
    @Value("${spring.redis.host}")
    private String hostName;
    @Value("${spring.redis.port}")
    private int port;

    /**
     * redis相关配置
     *
     * @param token ：token字符串
     * @param userName ：用户名
     * @param password ：密码
     * @return {Jedis}
     */
    public Jedis redisConfig(String token, String userName, String password) {
        final Jedis jedis = new Jedis(hostName, port);
        // 设置redis的key与value值
        jedis.set(TokenConstant.ACCESS_TOKEN, token);
        jedis.set(TokenConstant.USER_NAME, userName);
        long currentTime = System.currentTimeMillis();
        jedis.set(TokenConstant.TIME_STAMP, Long.toString(currentTime));
        // 设置key生存时间，当key过期时，它会被自动删除，时间是秒
        jedis.expire(TokenConstant.ACCESS_TOKEN, RedisConstant.TOKEN_EXPIRE_TIME);
        jedis.expire(TokenConstant.USER_NAME, RedisConstant.TOKEN_EXPIRE_TIME);
        jedis.expire(TokenConstant.TIME_STAMP, RedisConstant.TOKEN_EXPIRE_TIME);
        jedis.close();
        return jedis;
    }
}
