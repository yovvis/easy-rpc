package com.yovvis.ysrpc.fault.tolerant.failfast;

import com.yovvis.ysrpc.fault.tolerant.TolerantStrategy;
import com.yovvis.ysrpc.model.RpcResponse;

import java.util.Map;

/**
 * 快速失败 - 容错策略（立刻通知外层调用方）
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class FailFastTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        throw new RuntimeException("服务报错", e);
    }
}
