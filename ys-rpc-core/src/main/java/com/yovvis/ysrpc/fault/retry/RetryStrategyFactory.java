package com.yovvis.ysrpc.fault.retry;

import com.yovvis.ysrpc.fault.retry.noretry.NoRetryStrategy;
import com.yovvis.ysrpc.spi.SpiLoader;

/**
 * 重试策略-工厂
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class RetryStrategyFactory {
    static {
        SpiLoader.load(RetryStrategy.class);
    }

    /**
     * 默认重试器
     */
    private static final RetryStrategy DEFAULT_RETRY_STRATEGY = new NoRetryStrategy();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static RetryStrategy getInstance(String key) {
        return SpiLoader.getInstance(RetryStrategy.class, key);
    }
}
