package com.yovvis.ysrpc.proxy;

import com.yovvis.ysrpc.RpcApplication;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（创建代理对象）
 *
 * @author yovvis
 * @date 2024/3/5
 */
public class ServiceProxyFactory {
    /**
     * 根据服务获取代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        // Mock 判断
        if (RpcApplication.getRpcConfig().isMock()) {
            return getMockProxy(serviceClass);
        }
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new ServiceProxy());
    }

    /**
     * 根据服务类获取 Mock 代理对象
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    private static <T> T getMockProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new MockServiceProxy());
    }
}
