package org.forfun.mmorpg.client;

import org.forfun.mmorpg.common.util.JsonUtil;
import org.forfun.mmorpg.game.ConfigScanPaths;
import org.forfun.mmorpg.net.HostPort;
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

        HostPort hostPort = new HostPort();
        hostPort.setHost(ClientConfigs.REMOTE_SERVER_IP);
        hostPort.setPort(ClientConfigs.REMOTE_SERVER_PORT);

        SerializerFactory serializerFactory = ClientSerializerHelper.getInstance().getSerializerFactory();
        IMessageDispatcher msgDispatcher = new IMessageDispatcher() {
            @Override
            public void onSessionCreated(IdSession session) {

            }

            @Override
            public void dispatch(IdSession session, Message message) {
                System.err.println("收到消息<-- " + message.getClass().getSimpleName() + "=" + JsonUtil.object2String(message));
            }

            @Override
            public void onSessionClosed(IdSession session) {

            }
        };

        RpcClientFactory clientFactory = new RpcClientFactory(msgDispatcher, serializerFactory);
        IdSession session = clientFactory.createSession(hostPort);
        ClientRobot robot = new ClientRobot(session);
        robot.login();
        robot.selectedPlayer(10000L);
        robot.sendGm();
    }

}
