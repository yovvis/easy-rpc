package com.yovvis.ysrpc.springboot.starter.bootstrap;

import com.yovvis.ysrpc.RpcApplication;
import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.server.tcp.VertxTcpServer;
import com.yovvis.ysrpc.springboot.starter.annotation.EnableRpc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * RPC 框架启动
 *
 * @author yovvis
 * @date 2024/3/19
 */
@Slf4j
public class RpcInitBootStrap implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 1.获取 EnableRpc 注解的属性值
        boolean needServer = (Boolean) importingClassMetadata.getAnnotationAttributes(EnableRpc.class.getName()).get("needServer");
        // 2.RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
        // 3.全局配置
        final RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        // 4.启动服务器
        if (needServer) {
            VertxTcpServer vertxTcpServer = new VertxTcpServer();
            vertxTcpServer.doStart(rpcConfig.getServerPort());
        } else {
            log.info("不启动 server");
        }
    }
}
