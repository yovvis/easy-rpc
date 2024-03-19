package com.yovvis.ysrpc.fault.retry;

/**
 * 重试策略常量
 *
 * @author yovvis
 * @date 2024/3/19
 */
public interface RetryStrategyKeys {
    /**
     * 不重试
     */
    String NO = "no";

    /**
     * 固定时间间隔
     */
    String FIXED_INTERVAL = "fixInterval";
}
