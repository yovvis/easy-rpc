package com.yovvis.example.provider;

import com.yovvis.example.common.model.User;
import com.yovvis.example.common.service.UserService;

/**
 * 用户服务实现类
 *
 * @author
 * @date 2024/3/5
 */
public class UserServiceImpl implements UserService {
    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }
}
