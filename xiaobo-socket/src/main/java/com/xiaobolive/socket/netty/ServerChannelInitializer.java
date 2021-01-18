package com.xiaobolive.socket.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.CharsetUtil;

/**
 * @author Gjing
 *
 *         netty服务初始化器
 **/
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {
	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		// 添加编解码
//		socketChannel.pipeline().addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
//		socketChannel.pipeline().addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));

		socketChannel.pipeline().addLast("httpServerCodec", new HttpServerCodec());
		socketChannel.pipeline().addLast("chunkedWriteHandler", new ChunkedWriteHandler());
		socketChannel.pipeline().addLast("httpObjectAggregator", new HttpObjectAggregator(8192));
		socketChannel.pipeline().addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler("/websocket"));


//		socketChannel.pipeline().addLast("textWebSocketFrameHandler", new TextWebSocketFrameHandler());
//		socketChannel.pipeline().addLast(new NettyServerHandler());
		socketChannel.pipeline().addLast(new NettyWebSocketFrameHandler());
	}
}
