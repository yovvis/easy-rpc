package com.yovvis.example.consumer;

import com.yovvis.example.common.model.User;
import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.proxy.ServiceProxyFactory;

/**
 * 简单消费者示例
 *
 * @author yovvis
 * @date 2024/3/5
 */
public class EasyConsumerExample {
    public static void main(String[] args) {
        // 获取UserService实例对象
//        UserService userService = new UserServiceProxy();
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("Yovvis博客地址：https//blog.yovvis.top");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser != null) {
            System.out.println(newUser.getName());
        } else {
            System.out.println("user == null");
        }
    }
}
