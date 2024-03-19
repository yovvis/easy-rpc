package com.yovvis.ysrpc.server.tcp;

import com.yovvis.ysrpc.server.http.HttpServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;
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

        server.connectHandler(socket -> {
            // 半包，粘包测试
//            socket.handler(buffer -> {
//                String testMessage = "Hello,server!Hello,server!Hello,server!";
//                int messageLength = testMessage.getBytes().length;
//                if (buffer.getBytes().length < messageLength) {
//                    log.info("半包，length={}", buffer.getBytes().length);
//                    return;
//                }
//                if (buffer.getBytes().length > messageLength) {
//                    log.info("粘包，length={}", buffer.getBytes().length);
//                    return;
//                }
//                String str = new String(buffer.getBytes(0, messageLength));
//                log.info(str);
//                if (testMessage.equals(str)) {
//                    log.info("good");
//                }
//            });
            // 构造 parser
            RecordParser parser = RecordParser.newFixed(8);
            parser.setOutput(new Handler<Buffer>() {
                // 初始化
                int size = -1;
                // 一次性完整的读取（头+体）
                Buffer resultBuffer = Buffer.buffer();

                @Override
                public void handle(Buffer buffer) {
                    if (size == -1) {
                        // 读取消息体长度
                        size = buffer.getInt(4);
                        parser.fixedSizeMode(size);
                        // 写入头信息到结果
                        resultBuffer.appendBuffer(buffer);
                    } else {
                        // 写入信息到结果
                        resultBuffer.appendBuffer(buffer);
                        log.info(resultBuffer.toString());
                        // 重置一轮
                        parser.fixedSizeMode(8);
                        size = -1;
                        resultBuffer = Buffer.buffer();
                    }
                }
            });
            socket.handler(parser);
        });
        // 4.启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                log.info("TCP server started on: {}", port);
            } else {
                log.error("Failed to start TCP server: {}", result.cause());
            }
        });
    }

    public static void main(String[] args) {
        new VertxTcpServer().doStart(8888);
    }
}
