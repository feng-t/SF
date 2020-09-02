package com.sf.netty.proto;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * 协议解析
 */
public abstract class ProcessProtoResolve {
    private List<ChannelHandler> channelHandlers=new ArrayList<>();
    private boolean full=false;
    /**
     * 是否处理完
     * @return
     */
    boolean isProcess(ChannelHandlerContext cl, ByteBuf buf){
        if (isDecode(buf)){
            if (full){
                return true;
            }
            process(channelHandlers);
            add(cl);
            return true;
        }
        full=false;
        clear(cl);
        return false;
    };
    void clear(ChannelHandlerContext cl){
        for (ChannelHandler handler : channelHandlers) {
            cl.pipeline().remove(handler);
        }
        channelHandlers.clear();
    }
    void add(ChannelHandlerContext cl){
        ChannelPipeline pip = cl.pipeline();
        ChannelHandler[] array = new ChannelHandler[channelHandlers.size()];
        channelHandlers.toArray(array);
        pip.addLast(array);
        full=true;
    }
    /**
     * 通过数据判断是否可以解析，只会有一个是true
     * @param buf
     * @return
     */
    public abstract boolean isDecode(ByteBuf buf);
    /**
     * 将ChannelHandler 添加到list，随后list添加到ChannelPipeline，只执行一遍
     */
    public abstract void process(List<ChannelHandler> cls);


}
