package com.yovvis.ysrpc.springboot.starter.annotation;

import com.yovvis.ysrpc.springboot.starter.bootstrap.RpcConsumerBootStrap;
import com.yovvis.ysrpc.springboot.starter.bootstrap.RpcInitBootStrap;
import com.yovvis.ysrpc.springboot.starter.bootstrap.RpcProviderBootStrap;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用 RPC 注解
 *
 * @author yovvis
 * @date 2024/3/19
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({RpcInitBootStrap.class, RpcProviderBootStrap.class, RpcConsumerBootStrap.class})
public @interface EnableRpc {
    /**
     * 需要启动 server
     */
    boolean needServer() default true;
}
