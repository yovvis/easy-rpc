package com.yovvis.example.provider;

import com.yovvis.example.common.model.User;
import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.RpcApplication;
import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.registry.LocalRegistry;
import com.yovvis.ysrpc.server.HttpServer;
import com.yovvis.ysrpc.server.VertxHttpServer;
import com.yovvis.ysrpc.utils.ConfigUtils;

/**
 * 简单的服务者提供
 *
 * @author yovvis
 * @date 2024/3/5
 */
public class EasyProviderExample {
    public static void main(String[] args) {
//        // 注册服务
//        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
//
//        // 提供服务
//        HttpServer httpServer = new VertxHttpServer();
//        httpServer.doStart(8089);
        // 1.RPC 框架初始化
        RpcApplication.init();
        // 2.注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);
        // 3.启动服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
