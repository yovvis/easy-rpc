package com.yovvis.ysrpc.loadbalancer;

import com.yovvis.ysrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器常量
 *
 * @author yovvis
 * @date 2024/3/19
 */
public interface LoadBalancerKeys {
    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    /**
     * 随机
     */
    String RANDOM = "random";

    /**
     * 一致性 hash
     */
    String CONSISTENT_HASH = "consistentHash";
}
