package org.forfun.mmorpg.game.cross.service;

import org.forfun.mmorpg.game.cross.message.RpcReqHello;
import org.forfun.mmorpg.game.cross.util.CrossMessageUtil;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public void sayHello() {
        RpcReqHello req = new RpcReqHello();
        req.setContent("hello, game");

        CrossMessageUtil.requestToCenter(req).thenAccept(m -> {
            System.out.println(m);
        });
    }

}
