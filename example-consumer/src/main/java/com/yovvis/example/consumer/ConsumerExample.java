package com.yovvis.example.consumer;

import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.utils.ConfigUtils;

/**
 * 简单服务消费者实例
 *
 * @author yovvis
 * @date 2024/3/5
 */
public class ConsumerExample {
    public static void main(String[] args) {
        RpcConfig rpcConfig = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpcConfig);
    }
}
