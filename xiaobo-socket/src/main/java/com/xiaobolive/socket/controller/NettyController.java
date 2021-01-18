package com.xiaobolive.socket.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.*;
import com.xiaobolive.socket.common.ApplicationResource;
import com.xiaobolive.socket.netty.SyncFuture;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;

@Component
@RestController
@RequestMapping("/netty")
public class NettyController {

//	@Autowired
//	private NettyServerHandler nettyServerHandler;
	private SocketChannel socketChannel;

	public static LoadingCache<String, SyncFuture> futureCache = CacheBuilder.newBuilder().initialCapacity(100)
			.maximumSize(10000).concurrencyLevel(50).expireAfterWrite(8, TimeUnit.SECONDS)
			.removalListener(new RemovalListener<Object, Object>() {
				@Override
				public void onRemoval(RemovalNotification<Object, Object> notification) {
					System.out.println("locadionCache: " + notification.getKey() + " was removed ,cause is "
							+ notification.getCause());
				}
			}).build(new CacheLoader<String, SyncFuture>() {
				@Override
				public SyncFuture load(String key) throws Exception {
					return null;
				}
			});

	public String sendSyncMsg(String message, SyncFuture<String> syncFuture, ChannelHandlerContext ctx) {
		String result = "";
		try {
			ChannelFuture future = ctx.writeAndFlush(message);
			future.addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						System.out.println("发送成功：==============");
					} else {
						System.out.println("发送失败：==============");
					}

				}
			});
			result = syncFuture.get(20, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void ackSyncMsg(String msg) {
		try {
			System.out.println("fsdffsd" + msg);
			JSONObject json = JSON.parseObject(msg);
			SyncFuture<String> syncFuture = futureCache.get(json.getString("dataId"));
			if (syncFuture != null) {
				syncFuture.setResponse(msg);
				futureCache.invalidate(json.getString("dataId"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@PostMapping("/sendMsg")
	@ResponseBody
	public String sendsocket(@RequestBody Map<String, Object> map) {
//		nettyServerHandler
		for (String key : ApplicationResource.CONNECTED_CLIENTS_APK.keySet()) {
			if (key.equals(String.valueOf(map.get("userid")))) {
				ChannelHandlerContext tempctx = ApplicationResource.CONNECTED_CLIENTS_APK.get(key);
				SyncFuture<String> syncFuture = new SyncFuture<String>();
				futureCache.put("123", syncFuture);
				JSONObject postJson = new JSONObject();
				postJson.put("onEvent", "hahaha");
				postJson.put("dataId", "123");
				String resp = sendSyncMsg(postJson.toJSONString(), syncFuture, tempctx);
				System.out.println("真实返回：" + resp);
//				 NettyMessage message = (NettyMessage) msg;
//			        if (message != null && message.getType() == MessageType.SERVICE_RESP.value()) {
//			            response = message;
//			            promise.setSuccess();
//			        } else {
//			            ctx.fireChannelRead(msg);
//			        }
			}
		}
		return "fsadf";
	}
}
