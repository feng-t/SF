package com.sf.netty;

import com.sf.netty.initial.ServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class NettyServer {
    private int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        new NettyServer(8080).start();
    }

    private void start() throws IOException {
        /**
         * childHandler与Handler的区别: handler 表示在启动中需要经过哪些过程，childHandler 表示一条新连接进来之后进行的操作
         */
        NioEventLoopGroup worker = new NioEventLoopGroup();
        NioEventLoopGroup boss = new NioEventLoopGroup();

        try {
            ServerBootstrap bt = new ServerBootstrap();
            ChannelFuture f = bt.group(boss, worker).channel(NioServerSocketChannel.class)
//                    .handler(login)
                    .childHandler(new ServerInitializer())
                    .bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }


    }

    public void test() throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = loader.getResources("META-INF/spring.factories");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println(url);
        }
    }
}
