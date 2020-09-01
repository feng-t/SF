package com.sf.netty.initial;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();

//        pip.addLast(new ServerHandler());
//        pip.addLast(new HttpResponseEncoder(),
//                new HttpRequestDecoder(),
//                new NettyHttpServerHandler()
//        );
//        ChannelPipeline pip = ch.pipeline();
//        pip.addLast(new ProtobufVarint32FrameDecoder());
    }
}
