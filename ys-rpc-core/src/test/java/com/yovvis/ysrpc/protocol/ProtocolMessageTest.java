package com.yovvis.ysrpc.protocol;


import cn.hutool.core.util.IdUtil;
import com.yovvis.ysrpc.constant.ProtocolConstant;
import com.yovvis.ysrpc.constant.RpcConstant;
import com.yovvis.ysrpc.model.RpcRequest;
import com.yovvis.ysrpc.model.enums.ProtocolMessageSerializerEnum;
import com.yovvis.ysrpc.model.enums.ProtocolMessageStatusEnum;
import com.yovvis.ysrpc.model.enums.ProtocolMessageTypeEnum;
import io.vertx.core.buffer.Buffer;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class ProtocolMessageTest {

    @Test
    public void testEncodeAndDecode() throws IOException {
        // 构造消息
        ProtocolMessage<RpcRequest> protocolMessage = new ProtocolMessage<>();
        ProtocolMessage.Header header = new ProtocolMessage.Header();
        header.setMagic(ProtocolConstant.PROTOCOL_MAGIC);
        header.setVersion(ProtocolConstant.PROTOCAL_VERSION);
        header.setSerializer((byte) ProtocolMessageSerializerEnum.JDK.getKey());
        header.setType((byte) ProtocolMessageTypeEnum.REQUEST.getKey());
        header.setStatus((byte) ProtocolMessageStatusEnum.OK.getValue());
        header.setRequestId(IdUtil.getSnowflakeNextId());
        header.setBodyLength(0);

        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setServiceName("MyService");
        rpcRequest.setMethodName("myMethod");
        rpcRequest.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
        rpcRequest.setParameterTypes(new Class[]{String.class});
        rpcRequest.setArgs(new Object[]{"aaa", "bbb"});
        protocolMessage.setHeader(header);
        protocolMessage.setBody(rpcRequest);

        Buffer encodeBuff = ProtocolMessageEncoder.encode(protocolMessage);
        ProtocolMessage<?> message = ProtocolMessageDecoder.decode(encodeBuff);
        Assert.assertNotNull(message);
    }

}