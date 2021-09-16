package org.forfun.mmorpg.rpc.demo;

import org.forfun.mmorpg.rpc.server.RpcServer;
import org.forfun.mmorpg.rpc.server.RpcServerOptions;
import org.forfun.mmorpg.rpc.server.RpcServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages= {"org.forfun.mmorpg.rpc"})
public class ServerApp implements CommandLineRunner {

    @Autowired
    private RpcServiceRegistry serviceRegistry;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServerApp.class, args);
    }

    public void run(String... args) throws Exception {
        RpcServerOptions serverOptions = new RpcServerOptions();
        serverOptions.setPort(8868);
        RpcServer server = new RpcServer(serverOptions);
        server.start();
    }

}