package org.forfun.mmorpg.mq.produce;

import org.forfun.mmorpg.mq.MqG2FHello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProducerApp implements CommandLineRunner {

    @Autowired
    private MqProducerClient producerClient;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProducerApp.class, args);

    }

    public void run(String... args) throws Exception {
        MqG2FHello hello = new MqG2FHello();
        hello.setTargetSid(4567);
        producerClient.send(hello);
    }

}