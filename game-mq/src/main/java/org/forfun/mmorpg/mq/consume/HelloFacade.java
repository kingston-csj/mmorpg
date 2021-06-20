package org.forfun.mmorpg.mq.consume;

import org.forfun.mmorpg.mq.MqG2FHello;
import org.springframework.stereotype.Controller;

@Controller
public class HelloFacade {

    @MqHandler
    public void onSayHello(MqG2FHello hello) {
        System.out.println("received " + hello);
    }

}
