package com.xiaobolive.socket.netty;

import com.xiaobolive.socket.common.ApplicationResource;
import com.xiaobolive.socket.controller.NettyController;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Gjing
 *
 *         netty服务端处理器
 **/
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	/**
	 * 客户端连接会触发
	 */
	@Autowired
	private NettyController nettyc;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		NettyEvent
		System.out.println("Channel active......");
	}

	/**
	 * 客户端发消息会触发
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("服务器收到消息:" + msg.toString());
		System.out.println("服务器收到消息:" +  ((TextWebSocketFrame) msg).text());
//		JSONObject reviceJson = JSON.parseObject(msg.toString());
//
//		if ("init".equals(reviceJson.getString("onEvent"))) {
//			ApplicationResource.CONNECTED_CLIENTS_APK.put(reviceJson.getString("userid"), ctx);
//			System.out.println("初始化成功，userid：" + reviceJson.getString("userid"));
//		} else if ("hahaha".equals(reviceJson.getString("onEvent"))) {
//			System.out.println("server" + msg.toString());
////			nettyc.ackSyncMsg(msg.toString());
//			SyncFuture<String> syncFuture = NettyController.futureCache.get(reviceJson.getString("dataId"));
//			if (syncFuture != null) {
//				syncFuture.setResponse(msg.toString());
////				NettyController.futureCache.invalidate(reviceJson.getString("dataId"));
//			}
//		}
		TextWebSocketFrame tws = new TextWebSocketFrame("你快闭麦吧！！！！！！！！！！！");
		// 群发
		ctx.writeAndFlush(tws);
	}

	/**
	 * 发生异常触发
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("Channel closed......");
		cause.printStackTrace();
		ctx.close();
	}

}
