package com.xiaobolive.socket.service.impl;

import com.xiaobolive.socket.entity.User;
import com.xiaobolive.socket.mapper.UserMapper;
import com.xiaobolive.socket.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiaobo
 * @since 2021-01-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Value("${config.test}")
    private String test;
    @Override
    public String getConfig() {
        return test;
    }
}
