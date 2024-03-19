package com.yovvis.example.consumer;

import com.yovvis.example.common.model.User;
import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.bootstrap.ConsumerBootStrap;
import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.proxy.ServiceProxyFactory;
import com.yovvis.ysrpc.utils.ConfigUtils;

/**
 * 简单服务消费者实例
 *
 * @author yovvis
 * @date 2024/3/5
 */
public class ConsumerExample {
    public static void main(String[] args) {
        // 1.服务消费者初始化
        ConsumerBootStrap.init();
        // 2.获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("Yovvis博客地址：https//blog.yovvis.top");
        // 3.调用
        User newUser = userService.getUser(user);
        if (newUser == null) {
            System.out.println("user is null");
        } else {
            System.out.println(user.getName());
        }
    }
}
