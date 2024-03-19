package com.yovvis.ysrpc.loadbalancer;

import com.yovvis.ysrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器（消费端）
 *
 * @author yovvis
 * @date 2024/3/19
 */
public interface LoadBalancer {
    /**
     * 选择服务调用
     *
     * @param requestParams
     * @param serviceMetaInfoList
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
