package com.yovvis.ysrpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yovvis.ysrpc.RpcApplication;
import com.yovvis.ysrpc.config.RpcConfig;
import com.yovvis.ysrpc.constant.ProtocolConstant;
import com.yovvis.ysrpc.constant.RpcConstant;
import com.yovvis.ysrpc.model.RpcRequest;
import com.yovvis.ysrpc.model.RpcResponse;
import com.yovvis.ysrpc.model.ServiceMetaInfo;
import com.yovvis.ysrpc.model.enums.ProtocolMessageSerializerEnum;
import com.yovvis.ysrpc.model.enums.ProtocolMessageStatusEnum;
import com.yovvis.ysrpc.model.enums.ProtocolMessageTypeEnum;
import com.yovvis.ysrpc.protocol.ProtocolMessage;
import com.yovvis.ysrpc.protocol.ProtocolMessageDecoder;
import com.yovvis.ysrpc.protocol.ProtocolMessageEncoder;
import com.yovvis.ysrpc.registry.Registry;
import com.yovvis.ysrpc.registry.RegistryFactory;
import com.yovvis.ysrpc.serializer.Serializer;
import com.yovvis.ysrpc.serializer.SerializerFactory;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理（ JDK 动态代理）
 *
 * @author yovvis
 * @date 2024/3/5
 */
@Slf4j
public class ServiceProxy implements InvocationHandler {
    /**
     * 调用代理
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 1.指定序列化器
        final Serializer serializer = SerializerFactory.getInstance(RpcApplication.getRpcConfig().getSerializer());
        // 2.构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder().serviceName(serviceName).methodName(method.getName()).parameterTypes(method.getParameterTypes()).args(args).build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            // 从注册中心获取服务者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }
            ServiceMetaInfo selectedServiceMetaInfo = serviceMetaInfoList.get(0);
//            // 发送请求
//            // 地址被硬编码了（需要使用注册中心和服务发现机制解决）
//            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress()).body(bodyBytes).execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                // 反序列化
//                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
//                return rpcResponse.getData();
//            }
            // 发送 TCP 请求
            Vertx vertx = Vertx.vertx();
            NetClient netClient = vertx.createNetClient();
            CompletableFuture<RpcResponse> responseFuture = new CompletableFuture<>();
            netClient.connect(selectedServiceMetaInfo.getServicePort(), selectedServiceMetaInfo.getServiceHost(), result -> {
                if (result.succeeded()) {
                    log.info("Connected to TCP server");
                    NetSocket socket = result.result();
                    // 发送数据
                    // 构造消息
                    ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
                    ProtocolMessage.Header header = new ProtocolMessage.Header();

                    header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
                    header.setVersion(ProtocolConstant.PROTOCAL_VERSION);
                    header.setSerializer((byte) ProtocolMessageSerializerEnum.getEnumByValue(RpcApplication.getRpcConfig().getSerializer()).getKey());
                    header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
                    header.setRequestId(IdUtil.getSnowflakeNextId());

                    protocolMessage.setHeader(header);
                    protocolMessage.setBody(rpcRequest);
                    // 编码请求
                    try {
                        Buffer encodeBuffer = ProtocolMessageEncoder.encode(protocolMessage);
                        socket.write(encodeBuffer);
                    } catch (IOException e) {
                        throw new RuntimeException("协议消息编码错误");
                    }

                    // 接收响应
                    socket.handler(buffer -> {
                        try {
                            ProtocolMessage<RpcResponse> rpcResponseProtocolMessage = (ProtocolMessage<RpcResponse>) ProtocolMessageDecoder.decode(buffer);
                            responseFuture.complete(rpcResponseProtocolMessage.getBody());
                        } catch (IOException e) {
                            throw new RuntimeException("协议消息解码错误");
                        }
                    });
                } else {
                    log.error("Failed to connect to TCP server");
                }
            });
            RpcResponse rpcResponse = responseFuture.get();
            // 关闭连接
            netClient.close();
            return rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
