package com.yovvis.ysrpc.fault.tolerant.failsafe;

import com.yovvis.ysrpc.fault.tolerant.TolerantStrategy;
import com.yovvis.ysrpc.model.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 静默处理 - 容错策略
 *
 * @author yovvis
 * @date 2024/3/19
 */
@Slf4j
public class FailSafeTolerantStrategy implements TolerantStrategy {
    @Override
    public RpcResponse doTolerant(Map<String, Object> context, Exception e) {
        log.info("静默处理异常", e);
        return new RpcResponse();
    }
}
