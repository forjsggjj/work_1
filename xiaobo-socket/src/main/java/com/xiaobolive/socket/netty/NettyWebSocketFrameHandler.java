package com.xiaobolive.socket.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

public class NettyWebSocketFrameHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("收到消息:" + msg.toString());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("成功接收到客户端发送的：" + ((TextWebSocketFrame) msg).text()));
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器端返回：" + LocalDateTime.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //每个channel都有个全局唯一的id值 asLongText
        //asShortText不唯一
        System.out.println("handlerAdded：" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved：" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("发生异常");
        ctx.close();
    }


}
