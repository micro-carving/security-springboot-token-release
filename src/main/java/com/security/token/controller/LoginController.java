package com.security.token.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.auth0.jwt.JWT;
import com.security.token.annotation.AuthToken;
import com.security.token.api.Result;
import com.security.token.api.TokenGenerator;
import com.security.token.constant.RedisConstant;
import com.security.token.constant.TokenConstant;
import com.security.token.entity.User;
import com.security.token.mapper.IUserMapper;
import com.security.token.service.IUserService;
import com.security.token.utils.RedisUtil;
import com.security.token.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@RestController
@Slf4j
public class LoginController {

    @Resource
    TokenUtil tokenUtil;

    @Resource
    RedisUtil redisUtil;

    @Resource
    IUserService userService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome token authentication";
    }

//    @GetMapping("/test")
//    @AuthToken(required = false)
//    public Result<String> authToken() {
//        String res = "token有效！";
//        return Result.data(res);
//    }

    /**
     * 通过手动生成的token字符串进行登录
     *
     * @param userName ：用户名
     * @param password ：密码
     * @return {
     *     "code": 200,
     *     "message": "操作成功！",
     *     "data": {
     *         "token": "1f8a066b02400975eae6ac6ebfad579b",
     *         "status": "登录成功！"
     *     }
     * }
     */
    @GetMapping(value = "/login1")
    public Result<Map<String, Object>> login1(@RequestParam @Valid String userName, @RequestParam @Valid String password) {
        User user = userService.getUserByNameAndPwd(userName, password);
        final Map<String, Object> userMap = CollUtil.newHashMap();
        if (ObjectUtil.isNotNull(user)) {
            String token = tokenUtil.getTokenByManual(userName, password);
            final Jedis jedis = redisUtil.redisConfig(token, userName, password);
            userMap.put("token", jedis.get(TokenConstant.ACCESS_TOKEN));
        } else {
            userMap.put("status", "登录失败！");
        }
        return Result.data(userMap);
    }

    /**
     * 通过JWT生成的token字符串进行登录
     *
     * @param userName ：用户名
     * @param password ：密码
     * @return {
     *     "code": 200,
     *     "message": "操作成功！",
     *     "data": {
     *         "status": "登录成功！",
     *         "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtb2JpbGUiOiIxMTAiLCJ1c2VyTmFtZSI6InpoYW5nc2FuIiwiZXhwIjoxNjA0MjQyODgwLCJhdXRob3JpdGllcyI6Ii9yL3IxIn0.VnA7fZF_6DoOeoCcv1gnd26Qz0Tx03ZUo3o9grNrKMk"
     *     }
     * }
     */
    @GetMapping("/login2")
    public Result<Map<String, Object>> login2(@RequestParam @Valid String userName, @RequestParam @Valid String password){
        User user = userService.getUserByNameAndPwd(userName, password);
        final Map<String, Object> userMap = CollUtil.newHashMap();
        if (ObjectUtil.isNotNull(user)) {
            String token = tokenUtil.getTokenByJwt(user);
            final Jedis jedis = redisUtil.redisConfig(token, userName, password);
            userMap.put("token", jedis.get(TokenConstant.ACCESS_TOKEN));
        } else {
            userMap.put("status", "登录失败！");
        }
        return Result.data(userMap);
    }

    /**
     * 通过从请求头中获取token，验证token的合法性从而得到访问资源的权限
     *
     * @param token ：request对象
     * @return {String}
     */
    @AuthToken
    @GetMapping(value = "/r/r1")
    public String r1(@RequestHeader String token) {
        String userName;
        if (StrUtil.isEmpty(token)) {
            userName = "匿名";
        } else {
            userName = JWT.decode(token).getClaim("userName").asString();
        }
        return userName + "访问资源r1";
    }
}
