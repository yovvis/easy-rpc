package com.yovvis.ysrpc.fault.tolerant.failover;

import com.yovvis.ysrpc.fault.tolerant.TolerantStrategy;
import com.yovvis.ysrpc.model.RpcResponse;

import java.util.Map;

/**
 * 故障转移 - 容错策略（转移到其他服务节点）
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class FailOverTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 转到其他服务节点并调用
        return null;
    }
}
