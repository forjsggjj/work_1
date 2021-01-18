package com.xiaobolive.socket.netty;

import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Component
public class NettyServer {

	public static String host;
	public static int port;

	public String getHost() {
		return host;
	}

	@Value("${socket.host}")
	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	@Value("${socket.port}")
	public void setPort(int port) {
		this.port = port;
	}

//	@Value("${socket.host}")
//	private String socketHost;
//
//	@Value("${socket.port}")
//	private int socketPort;

	public void start() {
		start(new InetSocketAddress(host, port));
	}

	public void start(InetSocketAddress socketAddress) {
		// new 一个主线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		// new 一个工作线程组
		EventLoopGroup workGroup = new NioEventLoopGroup(200);
		ServerBootstrap bootstrap = new ServerBootstrap().group(bossGroup, workGroup)
				.channel(NioServerSocketChannel.class).childHandler(new ServerChannelInitializer())
				.localAddress(socketAddress)
				// 设置队列大小
				.option(ChannelOption.SO_BACKLOG, 1024)
				// 两小时内没有数据的通信时,TCP会自动发送一个活动探测数据报文
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				// 将小的数据包包装成更大的帧进行传送，提高网络的负载,即延迟传输
				.childOption(ChannelOption.TCP_NODELAY, true);
		// 绑定端口,开始接收进来的连接
		try {
			ChannelFuture future = bootstrap.bind(socketAddress).sync();
			System.out.println("服务器启动开始监听端口: " + socketAddress.getPort());
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 关闭主线程组
			bossGroup.shutdownGracefully();
			// 关闭工作线程组
			workGroup.shutdownGracefully();
		}
	}
}
