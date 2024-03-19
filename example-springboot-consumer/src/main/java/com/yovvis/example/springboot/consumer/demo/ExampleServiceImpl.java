package com.yovvis.example.springboot.consumer.demo;

import com.yovvis.example.common.model.User;
import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.springboot.starter.annotation.RpcReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 调用远程服务
 *
 * @author yovvis
 * @date 2024/3/19
 */
@Slf4j
@Service
public class ExampleServiceImpl {
    @RpcReference
    private UserService userService;

    public void testUser() {
        User user = new User();
        user.setName("Yovvis博客地址：https//blog.yovvis.top");
        User resultUser = userService.getUser(user);
        log.info("success!, {}", resultUser.getName());
    }
}
