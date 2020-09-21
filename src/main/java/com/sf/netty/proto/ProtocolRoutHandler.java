package com.sf.netty.proto;

import com.sf.netty.proto.parser.HttpProcessHandler;
import com.sf.netty.proto.parser.WsProcessHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ProtocolRoutHandler extends ChannelInboundHandlerAdapter {
    public Set<AbstractProcessProtoResolve> protoResolves=new HashSet<>();
    //TODO 临时
    public ProtocolRoutHandler(){
        protoResolves.add(new HttpProcessHandler());
        protoResolves.add(new WsProcessHandler());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Active");
        ChannelPipeline entries = ctx.pipeline();
        entries.addLast(new ChannelHandler() {
            @Override
            public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

            }

            @Override
            public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

            }
        });
        Map<String, ChannelHandler> map = entries.toMap();
        System.out.println(map.size());
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg.getClass());
        ByteBuf in = (ByteBuf) msg;
        if (!in.isReadable()){
            return;
        }


        for (AbstractProcessProtoResolve resolve : protoResolves) {
            if (resolve.isProcess(ctx,in.copy(0, in.readableBytes()))){
                break;
            }
        }
//        String s = new String(bytes);
//        System.out.println("----------------开始输出--------------");
//        System.out.println(s);
//        System.out.println("----------------结束输出--------------");



        ctx.fireChannelRead(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
