package com.security.token.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.security.token.entity.User;
import com.security.token.mapper.IUserMapper;
import com.security.token.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户业务实现类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/31
 */
@Service
public class UserServiceImpl extends ServiceImpl<IUserMapper, User> implements IUserService {

    @Resource
    IUserMapper userMapper;

    /**
     * 保存或者修改用户信息
     *
     * @param entity ：用户实体
     * @return ：{true|false}
     */
    @Override
    public boolean saveOrUpdate(User entity) {
        return super.saveOrUpdate(entity);
    }

    /**
     * 通过用户名获取
     *
     * @param userName ：用户名
     * @return {User}
     */
    @Override
    public User getByUserName(String userName) {
        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.orderByDesc("create_time");
        return getOne(queryWrapper);
    }

    /**
     * 通过用户名和密码获取用户
     *
     * @param userName ：用户名
     * @param password ：密码
     * @return {User}
     */
    @Override
    public User getUserByNameAndPwd(String userName, String password){
        final QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", userName);
        queryWrapper.eq("password", password);
        // return userMapper.getUserByNameAndPwd(userName, password);
        return getOne(queryWrapper);
    }
}
