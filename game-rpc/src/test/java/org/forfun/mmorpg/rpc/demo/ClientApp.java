package org.forfun.mmorpg.rpc.demo;

import org.forfun.mmorpg.rpc.client.RpcClient;
import org.forfun.mmorpg.rpc.client.RpcClientOptions;
import org.forfun.mmorpg.rpc.client.RpcClientProxy;

public class ClientApp {

    public static void main(String[] args) throws Exception {
        RpcClientOptions clientOptions = new RpcClientOptions();
        clientOptions.setPort(8868);
        clientOptions.setIpAddr("127.0.0.1");
        RpcClient client = new RpcClient(clientOptions);

        RpcClientProxy clientProxy = new RpcClientProxy(client, HelloService.class);
        HelloService helloService = (HelloService) clientProxy.proxy();

        for (int i = 0; i < 3; i++) {
            try {
                String ret = helloService.sayHi("world" + i);
//                helloService.toString();
                System.out.println("第" + i + "次请求：" + ret);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}