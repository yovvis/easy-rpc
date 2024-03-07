> RPC框架
>
> IDE：IDEA 2023.3.2
>
> JDK：jdk-11.0.19

## 1、介绍

**专业定义：** RPC(Remote Procedure Call) 远程过程调用，是一种计算机通信协议。

**功能：** 它允许程序在不同的计算机之间进行通信和交互，就像本地调用一样。

## 2、RPC框架图

![未命名绘图.drawio](https://doc-1315233939.cos.ap-shanghai.myqcloud.com/2024/202403061340333.png)

## 3、业务包拆分

- example-common：公共依赖，接口，Model等
- example-consumer：实例服务消费者代码
- example-provider：实例服务提供者代码
- ys-rpc-core：RPC核心（含rpc-essy功能）
- ys-rpc-easy：简易RPC框架

