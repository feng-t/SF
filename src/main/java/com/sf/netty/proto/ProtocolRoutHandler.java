package com.sf.netty.proto;

import com.sf.netty.handler.NettyHttpServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.util.ArrayList;
import java.util.List;

public class ProtocolRoutHandler extends ChannelInboundHandlerAdapter {
    public List<ProcessProtoResolve> protoResolves=new ArrayList<>();

    private static final String WEBSOCKET_PATH = "/websocket";
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Active");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.getClass());
        ByteBuf in = (ByteBuf) msg;
        if (!in.isReadable()){
            return;
        }
        byte[] bytes = new byte[in.readableBytes()];
        in.copy(0, in.readableBytes()).readBytes(bytes);

        for (ProcessProtoResolve resolve : protoResolves) {
            if (resolve.isProcess(ctx,bytes)){
                break;
            }
        }
//        String s = new String(bytes);
//        System.out.println("----------------开始输出--------------");
//        System.out.println(s);
//        System.out.println("----------------结束输出--------------");
        ChannelPipeline pipeline = ctx.pipeline();
        pipeline.addLast(new HttpResponseEncoder(),
                new HttpRequestDecoder(),
                new NettyHttpServerHandler()
        );


//        pipeline.addLast(new HttpServerCodec());
//        pipeline.addLast(new HttpObjectAggregator(65536));
//        pipeline.addLast(new WebSocketServerCompressionHandler());
//        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true));
////        pipeline.addLast(new WebSocketIndexPageHandler(WEBSOCKET_PATH));
//        pipeline.addLast(new WebSocketFrameHandler());
        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
