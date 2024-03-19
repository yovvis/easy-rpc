package com.yovvis.ysrpc.fault.retry.noretry;

import com.yovvis.ysrpc.fault.retry.RetryStrategy;
import com.yovvis.ysrpc.model.RpcResponse;

import java.util.concurrent.Callable;

/**
 * 不重试-重试策略
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class NoRetryStrategy implements RetryStrategy {
    /**
     * 重试
     *
     * @param callable
     * @return
     * @throws Exception
     */
    @Override
    public RpcResponse doRetry(Callable<RpcResponse> callable) throws Exception {
        return callable.call();
    }
}
