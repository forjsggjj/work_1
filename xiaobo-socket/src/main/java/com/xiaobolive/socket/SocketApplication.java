package com.xiaobolive.socket;

import com.xiaobolive.socket.netty.NettyServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.xiaobolive.socket.mapper")
@EnableSwagger2
@EnableScheduling
@SpringBootApplication
public class SocketApplication {

    public static void main(String[] args) {

        SpringApplication.run(SocketApplication.class, args);
        NettyServer nettyServer = new NettyServer();
        nettyServer.start();
    }

}
