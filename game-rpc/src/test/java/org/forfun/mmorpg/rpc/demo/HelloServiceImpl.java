package org.forfun.mmorpg.rpc.demo;

import org.forfun.mmorpg.rpc.server.RpcInvoker;
import org.forfun.mmorpg.rpc.server.RpcService;

@RpcService(name = "HelloService")
public class HelloServiceImpl implements HelloService {

    @Override
    @RpcInvoker
    public String sayHi(String request) {
        return "hi，" + request;
    }

}
