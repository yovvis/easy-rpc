package com.yovvis.ysrpc.constant;

/**
 * 协议常量类
 *
 * @author yovvis
 * @date 2024/3/17
 */
public interface ProtocolConstant {
    /**
     * 消息头长度
     */
    int MESSAGE_HEADER_LENGTH = 17;

    /**
     * 协议魔数
     */
    byte PROTOCOL_MAGIC = 0x1;

    /**
     * 协议版本号
     */
    byte PROTOCAL_VERSION = 0x1;
}
