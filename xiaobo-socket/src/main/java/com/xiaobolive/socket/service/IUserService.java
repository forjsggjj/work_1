package com.xiaobolive.socket.service;

import com.xiaobolive.socket.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiaobo
 * @since 2021-01-14
 */
public interface IUserService extends IService<User> {

    public String getConfig();
}
