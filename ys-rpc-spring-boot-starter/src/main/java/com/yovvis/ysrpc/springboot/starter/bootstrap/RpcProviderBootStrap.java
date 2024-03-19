package com.yovvis.ysrpc.springboot.starter.bootstrap;

import com.yovvis.ysrpc.RpcApplication;
import com.yovvis.ysrpc.config.RegistryConfig;
import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.model.ServiceMetaInfo;
import com.yovvis.ysrpc.registry.LocalRegistry;
import com.yovvis.ysrpc.registry.Registry;
import com.yovvis.ysrpc.registry.RegistryFactory;
import com.yovvis.ysrpc.springboot.starter.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 描述
 *
 * @author yovvis
 * @date 2024/3/19
 */
@Slf4j
public class RpcProviderBootStrap implements BeanPostProcessor {
    /**
     * Bean 初始化后执行，注册服务
     *
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        RpcService rpcService = beanClass.getAnnotation(RpcService.class);
        // 需要注册服务
        if (rpcService != null) {
            // 1.获取服务基本信息
            Class<?> interfaceClass = rpcService.interfaceClass();
            // 默认值处理
            if (interfaceClass == void.class) {
                interfaceClass = beanClass.getInterfaces()[0];
            }
            String serviceName = interfaceClass.getName();
            String serviceVersion = rpcService.serviceVersion();
            // 2.注册服务
            // 本地注册
            LocalRegistry.register(serviceName, beanClass);
            // 全局配置
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            // 3.注册服务到注册中心
            RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
            Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(serviceVersion);
            serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
            serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
            try {
                registry.register(serviceMetaInfo);
            } catch (Exception e) {
                throw new RuntimeException(serviceName + "服务注册失败" + e);
            }
        }
        return bean;
    }
}
