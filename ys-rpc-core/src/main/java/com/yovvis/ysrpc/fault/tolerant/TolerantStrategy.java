package com.yovvis.ysrpc.fault.tolerant;

import com.yovvis.ysrpc.model.RpcResponse;

import java.util.Map;

/**
 * 容错策略
 *
 * @author yovvis
 * @date 2024/3/19
 */
public interface TolerantStrategy {
    /**
     * 容错
     *
     * @param context 上下文
     * @param e
     * @return
     */
    RpcResponse doTolerant(Map<String, Object> context, Exception e);
}
