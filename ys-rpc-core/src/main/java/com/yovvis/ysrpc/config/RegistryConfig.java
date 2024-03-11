package com.yovvis.ysrpc.config;

import lombok.Data;

/**
 * RPC 框架注册中心配置
 *
 * @author yovvis
 * @date 2024/3/8
 */
@Data
public class RegistryConfig {
    /**
     * 注册中心类别
     */
    private String registry = "etcd";

    /**
     * 注册中心类别
     */
    private String address = "localhost:2380";

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 超时时间（单位毫秒）
     */
    private Long timeout = 10000L;


}
