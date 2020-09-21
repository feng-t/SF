package com.sf.netty.handler;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.nio.charset.StandardCharsets;

public class NettyHttpServerHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
//            ByteBuf in = content.content();
//            if (!in.isReadable()) {
//                return;
//            }
//            byte[] bytes = new byte[in.readableBytes()];
//            in.readBytes(bytes);
//            String s = new String(bytes);
//            System.out.println("----------------获取到--------------");
//            System.out.println(s);
//            System.out.println("----------------netty--------------");
        }
        if (msg instanceof HttpRequest){
//            HttpRequest req = (HttpRequest) msg;
//            System.out.println("------------------------req=----------------------");
//            System.out.println(req);
//            System.out.println("------------------------req----------------------");
        }
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer("撒打发斯蒂芬".getBytes(StandardCharsets.UTF_8)));
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}

//public class NettyHttpServerHandler extends ChannelInboundHandlerAdapter {
//
//    @Override
//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
////        ByteBuf in = (ByteBuf) msg;
////        if (!in.isReadable()){
////            return;
////        }
////        byte[] bytes = new byte[in.readableBytes()];
////        in.readBytes(bytes);
////        String s = new String(bytes);
////        System.out.println("----------------netty--------------");
////        System.out.println(s);
////        System.out.println("----------------netty--------------");
//
//
//        if (msg instanceof HttpContent){
//            HttpContent content = (HttpContent)msg;
//            ByteBuf buf = content.content();
//            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
//            buf.release();
//        }
//
//        FullHttpResponse response = new DefaultFullHttpResponse(
//                HttpVersion.HTTP_1_1,
//                HttpResponseStatus.OK,
//                Unpooled.wrappedBuffer("撒打发斯蒂芬".getBytes(StandardCharsets.UTF_8)));
//        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8");
//        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
//        response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        ctx.write(response);
//        ctx.flush();
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//        cause.printStackTrace();
//    }
//}
