package com.xiaobolive.socket.common;

import io.netty.channel.ChannelHandlerContext;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationResource {
    /**
     * 已连接的APK列表<br />
     * KEY -> {card_no}<br />
     * value -> socketVO
     */
    public static volatile Map<String, String> SNQRCODE_MAP = new ConcurrentHashMap<>();

    /**
     * 已连接的APK列表<br />
     * KEY -> {card_no}<br />
     * value -> socketVO
     */
    public static volatile Map<String, Date> CONNECTED_CLIENTS = new ConcurrentHashMap<>();

    /**
     * 已连接的APK列表<br />
     * KEY -> {card_no}<br />
     * value -> socketVO
     */
    public static volatile Map<String, ChannelHandlerContext> CONNECTED_CLIENTS_APK = new ConcurrentHashMap<>();

    /**
     * 已收到的订单数量<br />
     * KEY -> {card_no}<br />
     * value -> socketVO
     */
    public static volatile int ALL_SN_ORDER_NUM = 0;

}
