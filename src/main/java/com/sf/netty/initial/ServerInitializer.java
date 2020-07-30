package com.sf.netty.initial;

import com.sf.netty.handler.NettyHttpServerHandler;
import com.sf.netty.handler.ServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pip = ch.pipeline();
        pip.addLast(new ServerHandler());
        pip.addLast(new HttpResponseEncoder(),
                new HttpRequestDecoder(),
                new NettyHttpServerHandler()
        );
//        ChannelPipeline pip = ch.pipeline();
//        pip.addLast(new ProtobufVarint32FrameDecoder());
    }
}
