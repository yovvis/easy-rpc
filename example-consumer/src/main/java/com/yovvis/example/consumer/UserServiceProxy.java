package com.yovvis.example.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.yovvis.example.common.model.User;
import com.yovvis.example.common.service.UserService;
import com.yovvis.ysrpc.model.RpcRequest;
import com.yovvis.ysrpc.model.RpcResponse;
import com.yovvis.ysrpc.serializer.Serializer;
import com.yovvis.ysrpc.serializer.jdk.JdkSerializer;

import java.io.IOException;

/**
 * 静态代理
 *
 * @author yovvis
 * @date 2024/3/5
 */
@Deprecated
public class UserServiceProxy implements UserService {
    public User getUser(User user) {
        // 1.指定序列化器
        Serializer serializer = new JdkSerializer();
        // 2.发送请求
        RpcRequest rpcRequest = RpcRequest.builder().serviceName(UserService.class.getName()).methodName("getUser").parameterTypes(new Class[]{User.class}).args(new Object[]{user}).build();
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:8089").body(bodyBytes).execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (User) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
