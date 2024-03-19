package com.yovvis.example.provider;

import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.RpcApplication;
import com.yovvis.ysrpc.bootstrap.ProviderBootStrap;
import com.yovvis.ysrpc.config.RegistryConfig;
import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.model.ServiceMetaInfo;
import com.yovvis.ysrpc.model.ServiceRegisterInfo;
import com.yovvis.ysrpc.registry.LocalRegistry;
import com.yovvis.ysrpc.registry.Registry;
import com.yovvis.ysrpc.registry.RegistryFactory;
import com.yovvis.ysrpc.server.tcp.VertxTcpServer;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务提供实例
 *
 * @author yovvis
 * @date 2024/3/11
 */
public class ProviderExample {
    public static void main(String[] args) {
        // 1。要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(UserService.class.getName(), UserServiceImpl.class);
        serviceRegisterInfoList.add(serviceRegisterInfo);
        // 2.服务提供初始化
        ProviderBootStrap.init(serviceRegisterInfoList);
    }
}
