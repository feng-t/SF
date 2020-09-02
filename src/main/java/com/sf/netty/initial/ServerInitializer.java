package com.sf.netty.initial;

import com.sf.netty.proto.ProtocolRoutHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();
        //设置读写空闲监听器，避免死链接
        pip.addLast(new ReadTimeoutHandler(180),
                new WriteTimeoutHandler(180),
                new ProtocolRoutHandler());
    }
}
