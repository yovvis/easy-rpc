package com.yovvis.example.provider;

import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.RpcApplication;
import com.yovvis.ysrpc.config.RegistryConfig;
import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.model.ServiceMetaInfo;
import com.yovvis.ysrpc.registry.LocalRegistry;
import com.yovvis.ysrpc.registry.Registry;
import com.yovvis.ysrpc.registry.RegistryFactory;
import com.yovvis.ysrpc.server.VertxHttpServer;

/**
 * 服务提供实例
 *
 * @author yovvis
 * @date 2024/3/11
 */
public class ProviderExample {
    public static void main(String[] args) {
        // 1. RPC框架初始化
        RpcApplication.init();

        // 2.注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 3.注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceAddress(rpcConfig.getServerHost() + ":" + rpcConfig.getServerPort());

        serviceMetaInfo.setServiceVersion("1.0");
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 4.启动web服务
        VertxHttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());

    }
}
