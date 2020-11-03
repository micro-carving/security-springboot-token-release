package com.security.token.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.security.token.annotation.AuthToken;
import com.security.token.api.Result;
import com.security.token.constant.StatusCodeConstant;
import com.security.token.constant.TokenConstant;
import com.security.token.entity.User;
import com.security.token.entity.UserAuthenticationRequest;
import com.security.token.exception.TokenException;
import com.security.token.service.IAuthenticationService;
import com.security.token.service.IUserService;
import com.security.token.utils.RedisUtil;
import com.security.token.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 登录控制器
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

    @Resource
    IAuthenticationService authenticationService;

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome token authentication";
    }


    /**
     * 登录认证
     *
     * @param userAuthenticationRequest ：用户登录认证请求
     * @return {map}
     */
    @PostMapping(value = "/login")
    public Result<Map<String, Object>> login(@RequestBody @Valid UserAuthenticationRequest userAuthenticationRequest) {
        final Map<String, Object> userMap = CollUtil.newHashMap();
        final Map<String, Object> authenticationMap = authenticationService.userAuthentication(userAuthenticationRequest);
        final User user = (User) authenticationMap.get("user");
        if (ObjectUtil.isNotNull(user) && (Integer) authenticationMap.get("code") == StatusCodeConstant.HTTP_OK) {
            String token = tokenUtil.getTokenByJwt(user);
            final Jedis jedis = redisUtil.setProps(token, user.getUserName(), user.getAuthorities());
            userMap.put("token", jedis.get(TokenConstant.ACCESS_TOKEN));
        } else {
            return Result.fail(authenticationMap.get("message").toString());
        }
        return Result.data(userMap);
    }

    /**
     * 通过手动生成的token字符串进行登录，使用账号和用户名进行认证
     *
     * @param userName ：用户名
     * @param password ：密码
     * @return {
     * "code": 200,
     * "message": "操作成功！",
     * "data": {
     * "token": "1f8a066b02400975eae6ac6ebfad579b",
     * }
     * }
     */
    @GetMapping(value = "/login1")
    public Result<Map<String, Object>> login1(@RequestParam @Valid String userName, @RequestParam @Valid String password) {
        User user = userService.getUserByNameAndPwd(userName, password);
        final Map<String, Object> userMap = CollUtil.newHashMap();
        if (ObjectUtil.isNotNull(user)) {
            String token = tokenUtil.getTokenByManual(userName, password);
            final Jedis jedis = redisUtil.setProps(token, userName, user.getAuthorities());
            userMap.put("token", jedis.get(TokenConstant.ACCESS_TOKEN));
        } else {
            return Result.fail(userName + "登录失败！");
        }
        return Result.data(userMap);
    }

    /**
     * 通过JWT生成的token字符串进行登录，使用账号和用户名进行认证
     *
     * @param userName ：用户名
     * @param password ：密码
     * @return {
     * "code": 200,
     * "message": "操作成功！",
     * "data": {
     * "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJtb2JpbGUiOiIxMTAiLCJ1c2VyTmFtZSI6InpoYW5nc2FuIiwiZXhwIjoxNjA0MjQyODgwLCJhdXRob3JpdGllcyI6Ii9yL3IxIn0.VnA7fZF_6DoOeoCcv1gnd26Qz0Tx03ZUo3o9grNrKMk"
     * }
     * }
     */
    @GetMapping("/login2")
    public Result<Map<String, Object>> login2(@RequestParam @Valid String userName, @RequestParam @Valid String password) {
        User user = userService.getUserByNameAndPwd(userName, password);
        final Map<String, Object> userMap = CollUtil.newHashMap();
        if (ObjectUtil.isNotNull(user)) {
            String token = tokenUtil.getTokenByJwt(user);
            final Jedis jedis = redisUtil.setProps(token, userName, user.getAuthorities());
            userMap.put("token", jedis.get(TokenConstant.ACCESS_TOKEN));
        } else {
            return Result.fail(userName + "登录失败！");
        }
        return Result.data(userMap);
    }

    /**
     * 通过从请求头中获取token，验证token的合法性从而得到访问资源的权限，zhangsan拥有访问r1的权限
     *
     * @param token ：访问token字符串
     * @return {String}
     */
    @GetMapping(value = "/r/r1")
    public Result<Map<String, Object>> r1(@RequestHeader @NotNull String token, @RequestHeader @NotNull String userName) {
        final Map<String, Object> userMap = CollUtil.newHashMap();
        // 获取redis中存储的token值
        final String oldToken = redisUtil.getValueByKey(TokenConstant.ACCESS_TOKEN);
        // 获取redis中存储的用户名
        String oldUserName = redisUtil.getValueByKey(TokenConstant.USER_NAME);
        // 获取redis中存储的用户权限
        String oldAuthorities = redisUtil.getValueByKey(TokenConstant.AUTHORITIES);
        // 查询请求头用户名对应的数据库的用户数据
        final User user = userService.getByUserName(userName);
        // 判断请求头中的token与用户名以及用户权限是否与redis缓存存储的信息一致
        if (StrUtil.equals(token, oldToken) && StrUtil.equals(oldUserName, userName) && StrUtil.equals(oldAuthorities, user.getAuthorities())) {
            userMap.put("status", userName + "访问资源r1");
        } else {
            return Result.fail(userName + "无访问资源r1的权限！");
        }
        return Result.data(userMap);
    }

    /**
     * 通过从请求头中获取token，验证token的合法性从而得到访问资源的权限，lisi拥有访问r2的权限
     *
     * @param token ：访问token字符串
     * @return {String}
     */
    @AuthToken
    @GetMapping(value = "/r/r2")
    public Result<Map<String, Object>> r2(@RequestHeader @NotNull String token, @RequestHeader @NotNull String userName) {
        final Map<String, Object> userMap = CollUtil.newHashMap();
        final String oldToken;
        // 获取redis中存储的token值
        oldToken = redisUtil.getValueByKey(TokenConstant.ACCESS_TOKEN);
        // 获取jwt解密之后用户名与权限数据
        String oldUserName = JWT.decode(token).getClaim("userName").asString();
        String authorities = JWT.decode(token).getClaim("authorities").asString();
        // 查询请求头用户名对应的数据库的用户数据
        final User user = userService.getByUserName(userName);
        // 判断请求头中的token与用户名以及用户权限是否与redis缓存存储的信息一致
        if (StrUtil.equals(token, oldToken) && StrUtil.equals(oldUserName, userName) && StrUtil.equals(authorities, user.getAuthorities())) {
            userMap.put("status", userName + "访问资源r2");
        } else {
            return Result.fail(userName + "无访问资源r2的权限！");
        }
        return Result.data(userMap);
    }
}
