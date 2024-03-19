package com.yovvis.example.springboot.provider.service;

import com.yovvis.example.common.model.User;
import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.springboot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author yovvis
 * @date 2024/3/19
 */
@Slf4j
@Service
@RpcService
public class UserServiceImpl implements UserService {
    @Override
    public User getUser(User user) {
        log.info("用户名为 {}", user.getName());
        return user;
    }
}
