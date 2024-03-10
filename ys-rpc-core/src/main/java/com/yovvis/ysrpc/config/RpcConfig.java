package com.yovvis.ysrpc.config;

import com.yovvis.ysrpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架配置
 *
 * @author yovvis
 * @date 2024/3/5
 */
@Data
public class RpcConfig {
    /**
     * 名称
     */
    private String name = "ys-rpc";
    /**
     * 版本号
     */
    private String version = "1.0";
    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";
    /**
     * 服务器端口号
     */
    private int serverPort = 8089;

    /**
     * Mock 调用
     */
    private boolean mock = false;

    /**
     * 默认序列化器
     */
    private String serializer = SerializerKeys.JDK;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig;
}
