package com.yovvis.ysrpc.fault.tolerant.failback;

import com.yovvis.ysrpc.fault.tolerant.TolerantStrategy;
import com.yovvis.ysrpc.model.RpcResponse;

import java.util.Map;

/**
 * 故障恢复 -容错策略（降级到其他服务）
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class FailBackTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        // todo 获取降级的服务并调用
        return null;
    }
}
