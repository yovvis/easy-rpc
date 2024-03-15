package com.yovvis.ysrpc.model;

import cn.hutool.core.util.StrUtil;
import com.yovvis.ysrpc.constant.RpcConstant;
import lombok.Data;

/**
 * 服务注册信息
 *
 * @author yovvis
 * @date 2024/3/8
 */
@Data
public class ServiceMetaInfo {

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务版本号
     */
    private String serviceVersion = RpcConstant.DEFAULT_SERVICE_VERSION;

    /**
     * 服务地址（节点地址）
     */
    private String serviceAddress;

    /**
     * todo 服务分组
     */
    private String serviceGroup = RpcConstant.DEFAULT_SERVICE_GROUP;

    /**
     * 服务主机
     */
    private String serviceHost;

    /**
     * 服务端口
     */
    private int servicePort;


    /**
     * 获取服务键名
     *
     * @return
     */
    public String getServiceKey() {
        // todo 分组
//        return String.format("%s:%s:%s", serviceName, serviceVersion, serviceGroup);
        return String.format("%s:%s", serviceName, serviceVersion);
    }

    /**
     * 获取服务注册结点名
     *
     * @return
     */
    public String getServiceNodeKey() {
        return String.format("%s/%s:%s", getServiceKey(), serviceHost, servicePort);
    }

    /**
     * 获取完整服务地址
     *
     * @return
     */
    public String getServiceAddress() {
        if (!StrUtil.contains(serviceHost, "http")) {
            return String.format("http://%s:%s", serviceHost, servicePort);
        }
        return String.format("%s:%s", serviceHost, servicePort);
    }
}
