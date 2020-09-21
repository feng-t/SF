package com.sf.netty.proto.parser;

import com.sf.netty.handler.NettyHttpServerHandler;
import com.sf.netty.proto.AbstractProcessProtoResolve;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.util.Arrays;
import java.util.List;

public class HttpProcessHandler extends AbstractProcessProtoResolve {
    @Override
    public boolean isDecode(ByteBuf buf) {
//        byte[] bytes = new byte[buf.readableBytes()];
//        buf.readBytes(bytes);
//        System.out.println(new String(bytes));
        return true;
    }

    @Override
    public void process(List<ChannelHandler> ctx) {
        ctx.addAll(Arrays.asList(new HttpResponseEncoder(),
                new HttpRequestDecoder(),
                new NettyHttpServerHandler()));
    }
}
