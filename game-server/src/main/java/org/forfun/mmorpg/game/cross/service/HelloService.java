package org.forfun.mmorpg.game.cross.service;

import org.forfun.mmorpg.framework.net.Callback;
import org.forfun.mmorpg.game.cross.message.RpcReqHello;
import org.forfun.mmorpg.game.cross.util.CrossMessageUtil;
import org.forfun.mmorpg.protocol.Message;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    public void sayHello() {
        RpcReqHello req = new RpcReqHello();
        req.setContent("hello, game");

        Callback callback = new Callback() {
            @Override
            public void onMessageReceive(Message callBack) {
                System.out.println("receive <<- " );
            }

            @Override
            public void onError() {

            }
        };
        CrossMessageUtil.sendToCenter(req, callback);
    }


}
