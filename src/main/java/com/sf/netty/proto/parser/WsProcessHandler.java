package com.sf.netty.proto.parser;

import com.sf.netty.handler.WebSocketFrameHandler;
import com.sf.netty.proto.ProcessProtoResolve;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

import java.util.Arrays;
import java.util.List;

public class WsProcessHandler extends ProcessProtoResolve {
    private static final String WEBSOCKET_PATH = "/websocket";
    @Override
    public boolean isDecode(ByteBuf buf) {
        return false;
    }

    @Override
    public void process(List<ChannelHandler> cls) {
        List<ChannelInboundHandlerAdapter> asList = Arrays.asList(
                new HttpServerCodec(),
                new HttpObjectAggregator(65536),
                new WebSocketServerCompressionHandler(),
                new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true),
////        new WebSocketIndexPageHandler(WEBSOCKET_PATH)
                new WebSocketFrameHandler()
        );
        cls.addAll(asList);

    }
}
