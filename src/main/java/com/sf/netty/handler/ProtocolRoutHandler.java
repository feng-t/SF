package com.sf.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtocolRoutHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        if (!in.isReadable()){
            return;
        }
        byte[] bytes = new byte[in.readableBytes()];
        in.readBytes(bytes);
        String s = new String(bytes);
        System.out.println("----------------开始输出--------------");
        System.out.println(s);
        System.out.println("----------------结束输出--------------");
    }
}
