package com.zoyo.web.controller;

import cn.hutool.core.date.DateUtil;
import com.zoyo.web.component.MessageTask;
import com.zoyo.web.handler.SocketIoEventHandler;
import com.zoyo.web.service.IAudioInboxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 播客收件箱表 前端控制器
 * </p>
 *
 * @author mxx
 */
@Slf4j
@RestController
@RequestMapping("/box")
public class AudioInboxController {

    @Resource
    private SocketIoEventHandler ioEventHandler;

    @Resource
    private MessageTask messageTask;

    @Resource
    private IAudioInboxService inboxService;

    /**
     * 测试发送消息给客户端
     */
//    @Scheduled(cron = "*/5 * * * * ?")
    public void sendMsgToClient() {
        log.info("向客户端发送数据时间为：{}", DateUtil.now());
        ioEventHandler.pushToOnlineUsers(null);
    }

    @GetMapping("/t1")
    public void testSendMsg() {
        messageTask.sendNewMsgToSubscriber();
    }

    /**
     * 获取收件箱的消息
     *
     * @return result
     */
    @GetMapping("/news")
    public Object getNewMessage() {
        return this.inboxService.queryNewEnvelope();
    }

}
