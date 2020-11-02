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
     * 重载redis中get方法，设置键值对
     *
     * @param token    ：token字符串
     * @param userName ：用户名
     * @return {Jedis}
     */
    public Jedis setProps(String token, String userName, String authorities) {
        final Jedis jedis = new Jedis(hostName, port);
        // 设置redis的key与value值
        jedis.set(TokenConstant.ACCESS_TOKEN, token);
        jedis.set(TokenConstant.USER_NAME, userName);
        jedis.set(TokenConstant.AUTHORITIES, authorities);
        long currentTime = System.currentTimeMillis();
        jedis.set(TokenConstant.TIME_STAMP, Long.toString(currentTime));
        // 设置key生存时间，当key过期时，它会被自动删除，时间是秒
        jedis.expire(TokenConstant.ACCESS_TOKEN, RedisConstant.TOKEN_EXPIRE_TIME);
        jedis.expire(TokenConstant.USER_NAME, RedisConstant.TOKEN_EXPIRE_TIME);
        jedis.expire(TokenConstant.AUTHORITIES, RedisConstant.TOKEN_EXPIRE_TIME);
        jedis.expire(TokenConstant.TIME_STAMP, RedisConstant.TOKEN_EXPIRE_TIME);
        jedis.close();
        return jedis;
    }

    /**
     * 重载redis中get方法，获取键对应的值
     *
     * @param key ：键
     * @return {字符串的值}
     */
    public String getValueByKey(String key) {
        final Jedis jedis = new Jedis(hostName, port);
        return jedis.get(key);
    }
}
