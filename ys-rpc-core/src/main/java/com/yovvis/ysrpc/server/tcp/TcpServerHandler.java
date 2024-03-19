package com.yovvis.ysrpc.server.tcp;

import com.yovvis.ysrpc.model.RpcRequest;
import com.yovvis.ysrpc.model.RpcResponse;
import com.yovvis.ysrpc.model.enums.ProtocolMessageTypeEnum;
import com.yovvis.ysrpc.protocol.ProtocolMessage;
import com.yovvis.ysrpc.protocol.ProtocolMessageDecoder;
import com.yovvis.ysrpc.protocol.ProtocolMessageEncoder;
import com.yovvis.ysrpc.registry.LocalRegistry;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetSocket;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * TCP 请求处理
 *
 * @author yovvis
 * @date 2024/3/18
 */
public class TcpServerHandler implements Handler<NetSocket> {
    @Override
    public void handle(NetSocket socket) {
        TcpBufferHandlerWrapper bufferHandlerWrapper = new TcpBufferHandlerWrapper(buffer -> {
            // 接受请求，解码
            ProtocolMessage<RpcRequest> protocolMessage;
            try {
                protocolMessage = (ProtocolMessage<RpcRequest>) ProtocolMessageDecoder.decode(buffer);
            } catch (IOException e) {
                throw new RuntimeException("协议消息j解码错误");
            }
            RpcRequest rpcRequest = protocolMessage.getBody();
            // 2.处理请求
            // 构造响应结果对象
            RpcResponse rpcResponse = new RpcResponse();
            try {
                Class<?> implClass = LocalRegistry.get(rpcRequest.getServiceName());
                Method method = implClass.getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
                Object result = method.invoke(implClass.newInstance(), rpcRequest.getArgs());
                // 封装返回结果
                rpcResponse.setData(result);
                rpcResponse.setDataType(method.getReturnType());
                rpcResponse.setMessage("ok");
            } catch (Exception e) {
                e.printStackTrace();
                rpcResponse.setMessage(e.getMessage());
                rpcResponse.setException(e);
            }
            // 3.发送响应
            ProtocolMessage.Header header = protocolMessage.getHeader();
            header.setType((byte) ProtocolMessageTypeEnum.RESPONSE.getKey());
            ProtocolMessage<RpcResponse> responseProtocolMessage = new ProtocolMessage<>(header, rpcResponse);
            try {
                Buffer encode = ProtocolMessageEncoder.encode(responseProtocolMessage);
                socket.write(encode);
            } catch (IOException e) {
                throw new RuntimeException("协议消息解码错误");
            }
        });
        socket.handler(bufferHandlerWrapper);

//        // 1.处理连接
//        socket.handler(buffer -> {
//
//        });
    }
}
