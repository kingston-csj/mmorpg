package org.forfun.mmorpg.client;

import org.forfun.mmorpg.common.util.JsonUtil;
import org.forfun.mmorpg.game.ConfigScanPaths;
import org.forfun.mmorpg.net.client.ClientCfg;
import org.forfun.mmorpg.net.client.RpcClientFactory;
import org.forfun.mmorpg.net.dispatcher.IMessageDispatcher;
import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.net.message.MessageFactory;
import org.forfun.mmorpg.net.message.codec.SerializerFactory;
import org.forfun.mmorpg.net.socket.IdSession;

/**
 * 客户端模拟器启动程序
 */
public class ClientStartup {

    public static void main(String[] args) throws Exception {
        MessageFactory.getInstance().init(ConfigScanPaths.MESSAGE_BASE_PATH);

        ClientCfg clientCfg = new ClientCfg();
        clientCfg.setIpAddr(ClientConfigs.REMOTE_SERVER_IP);
        clientCfg.setPort(ClientConfigs.REMOTE_SERVER_PORT);

        SerializerFactory serializerFactory = ClientSerializerHelper.getInstance().getSerializerFactory();
        IMessageDispatcher msgDispatcher = new IMessageDispatcher() {
            @Override
            public void handle(IdSession session, Message message) {
                System.err.println("收到消息<-- " + message.getClass().getSimpleName() + "=" + JsonUtil.object2String(message));
            }
        };

        RpcClientFactory clientFactory = new RpcClientFactory(msgDispatcher, serializerFactory);
        IdSession session = clientFactory.createSession(clientCfg);
        ClientRobot robot = new ClientRobot(session);
        robot.login();
        robot.selectedPlayer(10000L);
        robot.sendGm();
    }

}
