package com.yovvis.ysrpc.model.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

/**
 * 协议消息状态枚举
 *
 * @author yovvis
 * @date 2024/3/17
 */
@Getter
public enum ProtocolMessageStatusEnum {

    OK("ok", 20),
    BAD_REQUEST("badrequest", 40),
    BAD_RESPONSE("badresponse", 50);

    private final String text;

    private final int value;

    ProtocolMessageStatusEnum(String text, int value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据 value获取枚举
     *
     * @param value
     * @return
     */
    public static ProtocolMessageStatusEnum getEnumByValue(int value) {
        for (ProtocolMessageStatusEnum anEnum : ProtocolMessageStatusEnum.values()) {
            if (anEnum.value == value) {
                return anEnum;
            }
        }
        return null;
    }

}
