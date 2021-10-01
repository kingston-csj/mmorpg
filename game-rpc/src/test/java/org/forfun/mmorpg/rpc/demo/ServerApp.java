package org.forfun.mmorpg.rpc.demo;

import org.forfun.mmorpg.rpc.server.RpcServer;
import org.forfun.mmorpg.rpc.server.RpcServerOptions;
import org.forfun.mmorpg.rpc.server.RpcServiceRegistry;

public class ServerApp {

    public static void main(String[] args) throws Exception {
        RpcServerOptions serverOptions = new RpcServerOptions();
        serverOptions.setPort(8868);
        RpcServiceRegistry rpcRegistry = new RpcServiceRegistry();
        rpcRegistry.register(new HelloServiceImpl());
        RpcServer server = new RpcServer(serverOptions, rpcRegistry);
        server.start();
    }


}