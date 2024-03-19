package com.yovvis.ysrpc.fault.retry;

import com.yovvis.ysrpc.fault.retry.noretry.NoRetryStrategy;
import com.yovvis.ysrpc.model.RpcResponse;
import org.junit.Test;

import static org.junit.Assert.*;

public class RetryStrategyTest {

    RetryStrategy retryStrategy = new NoRetryStrategy();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = retryStrategy.doRetry(() -> {
                System.out.println("重试测试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            throw new RuntimeException(e);
        }
    }
}