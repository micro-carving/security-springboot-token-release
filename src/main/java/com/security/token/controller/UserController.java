package com.security.token.controller;

import com.security.token.api.Result;
import com.security.token.entity.User;
import com.security.token.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 用户控制器
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/31
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    IUserService userService;

    /**
     * 请求url（localhost:8081/user/save），请求方式如下：
     * post请求，将实体信息放在请求体中，类型raw，格式application/json，形如：
     * {
     *     "userName": "wangwu",
     *     "password":"wangwu",
     *     "fullName":"wangwu",
     *     "mobile":"119",
     *     "authorities": "/r/r3",
     *     "createUserId": 111111111111111,
     *     "createTime": "2020-11-01 17:00:30",
     *     "updateUserId": null,
     *     "updateTime": "",
     *     "isDeleted": "0"
     * }
     *
     * @param user ：用户实体
     * @return ：{json形式的字符串}
     */
    @PostMapping(value = "/save")
    public Result<Boolean> save(@Valid @RequestBody User user) {
        return Result.status(userService.saveOrUpdate(user));
    }

    /**
     * 请求url（localhost:8081/user/getById/1322828903957520386），请求方式如下：
     * get请求，将请求参数参数直接作为路径变量。
     *
     * @param userId ：用户id
     * @return ：{json形式的字符串}
     */
    @GetMapping(value = "/getById/{userId}")
    public Result<User> getOneById(@PathVariable(value = "userId") @Valid Long userId) {
        return Result.data(userService.getById(userId));
    }

    /**
     * 请求url（localhost:8081/user/getByUserName?userName=wangwu），请求方式如下：
     * get请求，通过键值对形式的请求参数作为参数。
     *
     * @param userName ：用户名
     * @return ：{json形式的字符串}
     */
    @GetMapping(value = "/getByUserName")
    public Result<User> getByUserName(@RequestParam @Valid String userName) {
        return Result.data(userService.getByUserName(userName));
    }
}
