package com.yovvis.ysrpc.loadbalancer.consistenthash;

import cn.hutool.core.collection.CollUtil;
import com.yovvis.ysrpc.loadbalancer.LoadBalancer;
import com.yovvis.ysrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 一致性 hash 负载均衡器
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class ConsistentHashLoadBalancer implements LoadBalancer {

    /**
     * 一致性 hash 环，存放虚拟节点
     */
    private final TreeMap<Integer, ServiceMetaInfo> virtualNodes = new TreeMap<>();

    /**
     * 虚拟节点数
     */
    private static final int VIRTUAL_NODE_NUM = 100;

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            return null;
        }
        // 构建虚拟环节点
        for (ServiceMetaInfo serviceMetaInfo : serviceMetaInfoList) {
            for (int i = 0; i < VIRTUAL_NODE_NUM; i++) {
                int hash = getHash(serviceMetaInfo.getServiceAddress() + "#" + i);
                virtualNodes.put(hash, serviceMetaInfo);
            }
        }
        // 获取调用请求的 hash 值
        int hash = getHash(requestParams);

        // 选择 最接近 and >= 调用请求 hash 值的虚拟节点
        Map.Entry<Integer, ServiceMetaInfo> entry = virtualNodes.ceilingEntry(hash);
        if (entry == null) {
            // 如果没有 >= 调用请求 hash 值的虚拟节点，返回首部的节点
            entry = virtualNodes.firstEntry();
        }
        return entry.getValue();
    }

    /**
     * hash 算法
     *
     * @param key
     * @return
     */
    private int getHash(Object key) {
        return key.hashCode();
    }
}
