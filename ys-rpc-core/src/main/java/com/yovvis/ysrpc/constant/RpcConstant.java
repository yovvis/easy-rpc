package com.yovvis.ysrpc.constant;

/**
 * RPC 常量
 *
 * @author yovvis
 * @date 2024/3/5
 */
public interface RpcConstant {
    /**
     * 默认配置文件加载前缀
     */
    String DEFAULT_CONFIG_PREFIX = "rpc";

    /**
     * 默认服务版本
     */
    String DEFAULT_SERVICE_VERSION = "1.0";

    /**
     * 默认服务分组
     */
    String DEFAULT_SERVICE_GROUP = "default";

    /**
     * RPC etcd注册中心的根路径
     */
    String RPC_ETCD_ROOT_PATH = "/rpc/";

    /**
     * RPC zookeeper注册中心的根路径
     */
    String RPC_ZOOKEEPER_ROOT_PATH = "/rpc/zk";
}
