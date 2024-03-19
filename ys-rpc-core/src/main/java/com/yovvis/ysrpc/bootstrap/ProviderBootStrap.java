package com.yovvis.ysrpc.bootstrap;

import com.yovvis.ysrpc.RpcApplication;
import com.yovvis.ysrpc.config.RegistryConfig;
import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.model.ServiceMetaInfo;
import com.yovvis.ysrpc.model.ServiceRegisterInfo;
import com.yovvis.ysrpc.registry.LocalRegistry;
import com.yovvis.ysrpc.registry.Registry;
import com.yovvis.ysrpc.registry.RegistryFactory;
import com.yovvis.ysrpc.server.tcp.VertxTcpClient;
import com.yovvis.ysrpc.server.tcp.VertxTcpServer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 服务提供初始化
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class ProviderBootStrap {

    /**
     * 初始化
     *
     * @param serviceRegisterInfoList
     */
    public static void init(List<ServiceRegisterInfo<?>> serviceRegisterInfoList) {
        // 1. RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 2.全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        // 3.注册服务
        for (ServiceRegisterInfo<?> serviceRegisterInfo : serviceRegisterInfoList) {
            // 3.本地注册
            String serviceName = serviceRegisterInfo.getServiceName();
            LocalRegistry.register(serviceName, serviceRegisterInfo.getImpClass());
            // 4.注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());

            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败", e);
            }
        }
        // 5.启动服务器
        VertxTcpServer vertxTcpServer = new VertxTcpServer();
        vertxTcpServer.doStart(rpcConfig.getServerPort());
    }
}
