package com.yovvis.ysrpc.fault.tolerant;

import com.yovvis.ysrpc.fault.tolerant.failfast.FailFastTolerantStrategy;
import com.yovvis.ysrpc.loadbalancer.LoadBalancer;
import com.yovvis.ysrpc.loadbalancer.roundrobin.RoundRobinLoadBalancer;
import com.yovvis.ysrpc.spi.SpiLoader;

/**
 * 负载均衡器工厂
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class TolerantStartegyFactory {
    static {
        SpiLoader.load(TolerantStrategy.class);
    }

    /*
     * 默认的容错策略
     */
    private static final TolerantStrategy DEFAULT_TOLERANT_STRATEGY = new FailFastTolerantStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static TolerantStrategy getInstance(String key) {
        return SpiLoader.getInstance(TolerantStrategy.class, key);
    }

}
