package com.yovvis.ysrpc.registry.demo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Etcd 注册中心
 *
 * @author yovvis
 * @date 2024/3/8
 */
@Deprecated
public class EtcdRegistryDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1.客户端连接服务端口 2379提供 HTTP API 服务和 etcdctl交互
        Client client = Client.builder().endpoints("http://localhost:2379").build();
        // 2.使用 KV客户端
        KV kvClient = client.getKVClient();
        ByteSequence key = ByteSequence.from("test_key".getBytes());
        ByteSequence value = ByteSequence.from("test_value".getBytes());
        // 3.建立 key ->value
        kvClient.put(key, value).get();
        // 4.获取 Future
        CompletableFuture<GetResponse> getFuture = kvClient.get(key);
        // 5.从 Future取值
        GetResponse response = getFuture.get();
        // 6.删除 key值
        kvClient.delete(key).get();
    }
}
