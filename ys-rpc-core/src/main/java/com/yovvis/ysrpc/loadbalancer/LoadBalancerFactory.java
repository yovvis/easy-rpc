package com.yovvis.ysrpc.loadbalancer;

import com.yovvis.ysrpc.loadbalancer.roundrobin.RoundRobinLoadBalancer;
import com.yovvis.ysrpc.spi.SpiLoader;

/**
 * 负载均衡器工厂
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class LoadBalancerFactory {
    static {
        SpiLoader.load(LoadBalancer.class);
    }

    /*
     * 默认的负载均衡器
     */
    private static final LoadBalancer DEFAULT_LOAD_BALANCER = new RoundRobinLoadBalancer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static LoadBalancer getInstance(String key) {
        return SpiLoader.getInstance(LoadBalancer.class, key);
    }

}
