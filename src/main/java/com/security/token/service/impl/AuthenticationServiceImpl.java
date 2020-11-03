package com.security.token.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.token.entity.User;
import com.security.token.entity.UserAuthenticationRequest;
import com.security.token.enums.StatusCodeEnum;
import com.security.token.mapper.IUserMapper;
import com.security.token.service.IAuthenticationService;
import com.security.token.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 认证业务实现类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/11/3
 */
@Service
public class AuthenticationServiceImpl extends ServiceImpl<IUserMapper, User> implements IAuthenticationService {

    @Autowired
    IUserService userService;

    /**
     * 用户认证，校验用户身份（账号和密码）是否合法
     *
     * @param userAuthenticationRequest : 用户认证请求
     * @return {map}
     */
    @Override
    public Map<String, Object> userAuthentication(UserAuthenticationRequest userAuthenticationRequest) {
        final Map<String, Object> map = CollUtil.newHashMap();
        // 获取数据库中的数据
        final User user = userService.getByUserName(userAuthenticationRequest.getUserName());
        // 校验参数是否为空
        if (ObjectUtils.isEmpty(userAuthenticationRequest)
                || StringUtils.isEmpty(userAuthenticationRequest.getUserName())
                || StringUtils.isEmpty(userAuthenticationRequest.getPassword())) {
            map.put("message", "账号或者密码为空！");
        } else if (ObjectUtils.isEmpty(user)) {
            // 校验用户是否为空
            map.put("message", "查询的用户不存在！");
        } else if (!userAuthenticationRequest.getPassword().equals(user.getPassword())) {
            // 校验账号与密码是否错误
            map.put("message", "账号或者密码错误！");
        } else {
            map.put("message", StatusCodeEnum.SUCCESS.getMessage());
            map.put("code", StatusCodeEnum.SUCCESS.getCode());
            map.put("user", user);
        }
        // 认证通过，返回用户信息
        return map;
    }
}
