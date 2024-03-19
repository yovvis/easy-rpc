package com.yovvis.ysrpc.bootstrap;

import com.yovvis.ysrpc.RpcApplication;

/**
 * 服务消费者启动类（初始化）
 *
 * @author yovvis
 * @date 2024/3/19
 */
public class ConsumerBootStrap {
    /**
     * 初始化
     */
    public static void init(){
        // 1. RPC 框架初始化（配置和注册中心）
        RpcApplication.init();
    }
}
