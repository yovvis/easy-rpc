package com.yovvis.ysrpc.registry;

import com.yovvis.ysrpc.registry.etcd.EtcdRegistry;
import com.yovvis.ysrpc.spi.SpiLoader;

/**
 * 注册中心工厂（获取注册中心对象）
 *
 * @author yovvis
 * @date 2024/3/10
 */
public class RegistryFactory {
    static {
        SpiLoader.load(Registry.class);
    }

    /**
     * 默认注册中心
     */
    private static final Registry DEFAULT_REGISTRY = new EtcdRegistry();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Registry getInstance(String key) {
        return SpiLoader.getInstance(Registry.class, key);
    }
}
