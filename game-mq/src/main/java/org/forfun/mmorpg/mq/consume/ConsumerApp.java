package org.forfun.mmorpg.mq.consume;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.forfun.mmorpg.mq.MqMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

@SpringBootApplication
public class ConsumerApp {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConsumerApp.class, args);
    }

    @Autowired
    private MqHandlerDispatcher messageDispatcher;

    @Service
    @RocketMQMessageListener(topic = "${rocketmq.topic:}", selectorExpression = "${rocketmq.tag:}",
            consumerGroup = "${rocketmq.producer.group:}",
            messageModel = MessageModel.BROADCASTING)
    public class MessageConsumer implements RocketMQListener<MqMessage> {
        @Override
        public void onMessage(MqMessage message) {
            System.out.printf("######## mq_consumer received: %s  \n", message);
            messageDispatcher.dispatch(message);
        }
    }


}