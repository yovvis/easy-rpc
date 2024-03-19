package com.yovvis.ysrpc.loadbalancer.random;

import cn.hutool.core.collection.CollUtil;
import com.yovvis.ysrpc.loadbalancer.LoadBalancer;
import com.yovvis.ysrpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 随机负载均衡
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class RandomLoadBalancer implements LoadBalancer {

    private final Random random = new Random();

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        if (CollUtil.isEmpty(serviceMetaInfoList)) {
            return null;
        }
        // 只有一个服务，返回
        int size = serviceMetaInfoList.size();
        if (size == 1) {
            return serviceMetaInfoList.get(0);
        }
        return serviceMetaInfoList.get(random.nextInt(size));
    }
}
