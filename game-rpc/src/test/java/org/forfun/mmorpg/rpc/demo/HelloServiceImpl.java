package org.forfun.mmorpg.rpc.demo;

import org.forfun.mmorpg.rpc.server.RpcService;
import org.springframework.stereotype.Component;

@Component
@RpcService(name = "HelloService")
public class HelloServiceImpl implements HelloService{

    @Override
    public String sayHi(String request) {
        return "hi，" + request;
    }
}
