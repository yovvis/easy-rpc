package com.yovvis.ysrpc.server;

/**
 * HTTP服务器接口
 *
 * @author yovvis
 * @date 2024/3/5
 */
public interface HttpServer {
    /**
     * 启动容器
     *
     * @param port
     */
    void doStart(int port);
}
