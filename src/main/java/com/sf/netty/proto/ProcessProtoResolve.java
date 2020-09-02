package com.sf.netty.proto;

import io.netty.channel.ChannelHandlerContext;

/**
 * 协议解析
 */
public abstract class ProcessProtoResolve {

    /**
     * 是否处理完
     * @return
     */
    boolean isProcess(ChannelHandlerContext cl,byte[] dates){
        if (isNext(dates)){
            process(cl);
            return true;
        }
        return false;
    };
    abstract boolean isNext(byte[] b);
    /**
     * 解析协议
     */
    abstract void process(ChannelHandlerContext cl);
}
