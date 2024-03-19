package com.yovvis.ysrpc.server.tcp;

import com.yovvis.ysrpc.server.http.HttpServer;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetServer;
import lombok.extern.slf4j.Slf4j;

/**
 * Tcp 服务器实现
 *
 * @author yovvis
 * @date 2024/3/18
 */
@Slf4j
public class VertxTcpServer implements HttpServer {

    private byte[] handleRequest(byte[] requestData) {
        // 处理请求逻辑，根据 requestData 构造响应数据
        // 仅仅是示例，需要具体业务逻辑支撑
        return "Hello,client！".getBytes();
    }

    @Override
    public void doStart(int port) {
        // 1.创建 Vertx 实例
        Vertx vertx = Vertx.vertx();
        // 2.创建 TCP 服务器
        NetServer server = vertx.createNetServer();
        // 3.处理请求
        server.connectHandler(new TcpServerHandler());
        // 4.启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                log.info("TCP server started on: {}", port);
            } else {
                log.error("Failed to start TCP server: {}", result.cause());
            }
        });
    }
}
