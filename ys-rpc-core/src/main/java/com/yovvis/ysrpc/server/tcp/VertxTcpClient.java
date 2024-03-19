package com.yovvis.ysrpc.server.tcp;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

/**
 * Tcp 客户端实现
 *
 * @author yovvis
 * @date 2024/3/18
 */
@Slf4j
public class VertxTcpClient {
    public void start() {
        // 1. 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        vertx.createNetClient().connect(8888, "localhost", result -> {
            if (result.succeeded()) {
                log.info("Connect to TCP server");
                NetSocket socket = result.result();
                for (int i = 0; i < 1000; i++) {
                    // 发送数据
//                    socket.write("Hello,server!Hello,server!Hello,server!");
                    // 半包，粘包测试
                    Buffer buffer = Buffer.buffer();
                    String str = "Hello,server!Hello,server!Hello,server!";
                    buffer.appendInt(0);
                    buffer.appendInt(str.getBytes().length);
                    buffer.appendBytes(str.getBytes());
                    socket.write(buffer);
                }
                // 接收响应
                socket.handler(buffer -> {
                    log.info("Received response from server: {}", buffer.toString());
                });
            } else {
                log.error("Failed to connect TCP server");
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpClient().start();
    }
}
