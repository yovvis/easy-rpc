package com.yovvis.ysrpc.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yovvis.ysrpc.model.RpcRequest;
import com.yovvis.ysrpc.model.RpcResponse;

import java.io.IOException;

/**
 * Json 序列化器
 *
 * @author yovvis
 * @date 2024/3/6
 */
public class JsonSerializer implements Serializer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 序列化
     *
     * @param object
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> byte[] serialize(T object) throws IOException {
        return OBJECT_MAPPER.writeValueAsBytes(object);
    }

    /**
     * 反序列化
     *
     * @param bytes
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> tClass) throws IOException {
        T obj = OBJECT_MAPPER.readValue(bytes, tClass);
        if (obj instanceof RpcRequest) {
            return handleRequest((RpcRequest) obj, tClass);
        }
        if (obj instanceof RpcResponse) {
            return handleResponse((RpcResponse) obj, tClass);
        }
        return obj;
    }

    /**
     * Object原始对象会被擦除，导致反序列化会被作为 LinkedHashMap 无法转成原始对象，这里特殊处理
     *
     * @param rpcRequest
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T> T handleRequest(RpcRequest rpcRequest, Class<T> type) throws IOException {
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Object[] args = rpcRequest.getArgs();

        // 循环处理每个参数类型
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> clazz = parameterTypes[i];
            // 如果类型不同，则重新处理类型
            if (!clazz.isAssignableFrom(args[i].getClass())) {
                byte[] argBytes = OBJECT_MAPPER.writeValueAsBytes(args[i]);
                args[i] = OBJECT_MAPPER.readValue(argBytes, clazz);
            }
        }
        return type.cast(rpcRequest);
    }

    /**
     * Object原始对象会被擦除，导致反序列化会被作为 LinkedHashMap 无法转成原始对象，这里特殊处理
     *
     * @param rpcResponse
     * @param type
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T> T handleResponse(RpcResponse rpcResponse, Class<T> type) throws IOException {
        // 处理响应数据
        byte[] dataBytes = OBJECT_MAPPER.writeValueAsBytes(rpcResponse.getData());
        rpcResponse.setData(OBJECT_MAPPER.readValue(dataBytes, rpcResponse.getDataType()));
        return type.cast(rpcResponse);
    }
}
