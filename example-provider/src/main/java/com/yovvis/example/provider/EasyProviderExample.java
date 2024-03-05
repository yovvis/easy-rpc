package com.yovvis.example.provider;

import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.registry.LocalRegistry;
import com.yovvis.ysrpc.server.HttpServer;
import com.yovvis.ysrpc.server.VertxHttpServer;

/**
 * 简单的服务者提供
 *
 * @author
 * @date 2024/3/5
 */
public class EasyProviderExample {
    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 提供服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(8089);
    }
}
