package com.yovvis.ysrpc.server;

import io.vertx.core.Vertx;

/**
 * 基于Vert.x实现的web服务器的VertxHttpServer
 *
 * @author yovvis
 * @date 2024/3/5
 */
public class VertxHttpServer implements HttpServer {
    public void doStart(int port) {
        // 1.创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 2.创建 HTTP 服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();

        // 3.监听端口并处理请求（使用自定义的请求处理器）
        server.requestHandler(new HttpServerHandler());
//        server.requestHandler(request -> {
//            // 处理 HTTP 请求
//            System.out.println("Receive request：" + request.method() + " " + request.uri());
//            // 发送 HTTP 响应
//            request.response().putHeader("content-type", "text/plain").end("Hello from Vert.x HTTP server!");
//        });
        // 4.启动 HTTP 服务器并指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                System.out.println("Server is now listening on port：" + port);
            } else {
                System.out.println("Fail to start server：" + result.cause());
            }
        });
    }
}
