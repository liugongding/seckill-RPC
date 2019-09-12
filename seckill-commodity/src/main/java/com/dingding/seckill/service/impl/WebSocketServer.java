package com.dingding.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.dingding.seckill.dto.SeckillExecution;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Description
 * @Author liugongding
 * @Date 2019-09-11
 */

@ServerEndpoint(value = "/ws")
@Component
public class WebSocketServer {

    //此处是解决无法注入的关键
    private static ApplicationContext applicationContext;
    //你要注入的service或者dao
    private CommodityServiceImpl commodityService;

    public static void setApplicationContext(ApplicationContext applicationContext) {
        WebSocketServer.applicationContext = applicationContext;
    }

    /**
     * 存放客户端websocket对象
     */
    public static CopyOnWriteArraySet<WebSocketServer> websocketSet = new CopyOnWriteArraySet<>();

    /**
     * 连接会话
     */
    private Session session;


    /**
     * 开启连接
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        websocketSet.add(this);
        System.out.println("有一个客户端连接");
    }

    /**
     * 断开连接
     */
    @OnClose
    public void onClose() {
        websocketSet.remove(this);
        System.out.println("有一个客户断开连接");
    }

    /**
     * 发生错误
     *
     * @param error
     * @param session
     */
    @OnError
    public void onError(Throwable error, Session session) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 来自客户端消息
     *
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        System.out.println("来自客户端的消息：" + message);
        Integer commodityId = Integer.parseInt(message.substring(0, 4));
        Long userPhone = Long.valueOf(message.substring(4, 15));
        System.out.println(commodityId);
        System.out.println(userPhone);
        Integer commodityId_userPhone = commodityId + userPhone.intValue();
        String msg = commodityId_userPhone.toString();
        System.out.println(msg);
        sendText(msg);
    }

    /**
     * 服务端向客户端发送消息
     *
     * @param msg
     * @throws IOException
     */
    public void sendText(String msg) throws IOException, InterruptedException {


        Integer commodityId_userPhone = Integer.parseInt(msg);
        System.out.println("服务向客户端发送消息:" + commodityId_userPhone);

        commodityService = applicationContext.getBean(CommodityServiceImpl.class);
        System.out.println(commodityService);
        SeckillExecution seckillExecution = commodityService.querySeckillStatus(commodityId_userPhone);
        System.out.println(seckillExecution);
        this.session.getBasicRemote().sendText(JSON.toJSONString(seckillExecution));
    }
}
