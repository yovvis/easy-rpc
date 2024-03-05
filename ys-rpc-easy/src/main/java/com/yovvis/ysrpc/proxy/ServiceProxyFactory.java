package com.yovvis.ysrpc.proxy;

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
     * @return
     * @param <T>
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new ServiceProxy());
    }
}
