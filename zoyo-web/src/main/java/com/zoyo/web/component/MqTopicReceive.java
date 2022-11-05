package com.zoyo.web.component;

import cn.hutool.core.convert.Convert;
import com.zoyo.common.constant.RedisConstant;
import com.zoyo.common.constant.SysConstant;
import com.zoyo.web.handler.SocketIoEventHandler;
import com.zoyo.web.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @Author: mxx
 * @Description: mq消息接收者
 */
@Slf4j
@Component
//@RabbitListener(queuesToDeclare = @Queue(name = SysConstant.TOPIC_QUEUE_NAME))
//@RabbitListener(queues = "zy.web.queue")
@RabbitListener(queues = SysConstant.TOPIC_QUEUE_NAME)
public class MqTopicReceive {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private SocketIoEventHandler socketIoEventHandler;

    /**
     * mq接受者
     * <p>
     * //     * @param msg msg
     */
    @RabbitHandler
    public void receiveMsg2(Object msg) {
        log.info("mq接收到消息:{}", msg);

        Set<Object> objects = redisUtil.zRangeByScore(RedisConstant.SOCKET_ONLINE, 0, 5);

        List<String> onLineUsers = Convert.toList(String.class, objects);
        for (String userId : onLineUsers) {
            String num = "2";
            socketIoEventHandler.pushToOnlineUser(userId,
                    SysConstant.WS_SINGLE_PASSAGE, num);

        }
    }

//    /**
//     * mq接受者
//     *
//     * @param data data
//     */
//    @RabbitHandler
//    public void receiveMsg(@Payload Object data, Message message, Channel channel) {
//        try {
//            log.info("接收到消息：{}", new String(message.getBody()));
//            log.info("message：{}", message.toString());
//            log.info("body：{}", data);
//            // multiple 取值为 false 时，表示通知 RabbitMQ 当前消息被确认
//            // 如果为 true，则额外将比第一个参数指定的 delivery tag 小的消息一并确认
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (Exception e) {
//            // 两个布尔值  第二个设为 false 则丢弃该消息 设为true 则返回给队列
//            try {
//                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//            } catch (IOException e1) {
//                log.error("receiveMsg() 返回给队列失败，原因是 :{}", e1.getMessage());
//                e1.printStackTrace();
//            }
//        }
//    }

}
