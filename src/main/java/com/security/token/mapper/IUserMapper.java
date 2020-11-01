package com.security.token.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.security.token.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户映射器类
 *
 * @author : OlinH
 * @version : v1.0
 * @since : 2020/10/30
 */
@Mapper
public interface IUserMapper extends BaseMapper<User> {

    /**
     * 通过用户名和密码获取用户
     *
     * @param userName ：用户名
     * @param password ：密码
     * @return {User}
     */
    User getUserByNameAndPwd(@Param("userName") String userName, @Param("password") String password);
}
