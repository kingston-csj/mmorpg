package org.forfun.mmorpg.mq.produce;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.forfun.mmorpg.mq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class MqProducerClient {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.topic}")
    private String mqTopic;

    @Value("${rocketmq.tag}")
    private String mqFlag;

    /**
     * 有序发送mq消息（顺序为hash(destination)）
     * @param message
     */
    public void send(MqMessage message) {
       sendWithTag(message, String.valueOf(message.receiver()));
    }

    public void sendWithTag(MqMessage message, String tag) {
        String topicAndFlag = mqTopic + ":" + tag;
        SendResult sendResult =
                rocketMQTemplate.syncSendOrderly(topicAndFlag, MessageBuilder.withPayload(
                        message).setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE).build(),
                        topicAndFlag);
        System.out.printf("send msg [%s] to topic %s sendResult=%s %n", message, topicAndFlag, sendResult);
    }

}
