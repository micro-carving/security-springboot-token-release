package com.security.token.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.security.token.entity.User;

/**
 * 用户业务类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
public interface IUserService extends IService<User> {
    /**
     * 通过用户名获取
     *
     * @param userName ：用户名
     * @return {User}
     */
    User getByUserName(String userName);

    /**
     * 通过用户名和密码获取用户
     *
     * @param userName ：用户名
     * @param password ：密码
     * @return {User}
     */
    User getUserByNameAndPwd(String userName, String password);

}
