package com.yovvis.example.consumer;

import com.yovvis.example.common.model.User;
import com.yovvis.example.common.service.UserService;
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
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpcConfig);
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);
        User user = new User();
        user.setName("你好, 我是Yovvis");
        // 调用
        User newUser = userService.getUser(user);
        if (newUser == null) {
            System.out.println("user is null");
        } else {
            System.out.println(user.getName());
        }
        long number = userService.getNumber();
        System.out.println(number);
    }
}
