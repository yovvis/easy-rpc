package com.yovvis.ysrpc.server.tcp;

import com.yovvis.ysrpc.constant.ProtocolConstant;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;

/**
 * TCP 请求处理 装饰者模式（ recordParser 对 buffer 处理能力增强）
 *
 * @author yovvis
 * @date 2024/3/18
 */
public class TcpBufferHandlerWrapper implements Handler<Buffer> {

    private final RecordParser recordParser;

    public TcpBufferHandlerWrapper(Handler<Buffer> bufferHandler) {
        recordParser = initRecordParser(bufferHandler);
    }

    @Override
    public void handle(Buffer buffer) {
        recordParser.handle(buffer);
    }

    /**
     * 增强 buffer
     *
     * @param bufferHandler
     * @return
     */
    private RecordParser initRecordParser(Handler<Buffer> bufferHandler) {
        // 构造 parser
        RecordParser parser = RecordParser.newFixed(ProtocolConstant.MESSAGE_HEADER_LENGTH);
        parser.setOutput(new Handler<Buffer>() {
            // 初始化
            int size = -1;
            // 一次性完整的读取（头+体）
            Buffer resultBuffer = Buffer.buffer();

            @Override
            public void handle(Buffer buffer) {
                if (size == -1) {
                    // 读取消息体长度
                    size = buffer.getInt(13);
                    parser.fixedSizeMode(size);
                    // 写入头信息到结果
                    resultBuffer.appendBuffer(buffer);
                } else {
                    // 写入信息到结果
                    resultBuffer.appendBuffer(buffer);
                    // 已经拼接完整 buffer 执行
                    bufferHandler.handle(resultBuffer);
                    // 重置一轮
                    parser.fixedSizeMode(ProtocolConstant.MESSAGE_HEADER_LENGTH);
                    size = -1;
                    resultBuffer = Buffer.buffer();
                }
            }
        });
        return parser;
    }
}
