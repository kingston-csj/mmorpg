package org.forfun.mmorpg.client;

import jforgame.codec.struct.StructMessageCodec;
import jforgame.socket.client.SocketClient;
import jforgame.socket.netty.support.client.TcpSocketClient;
import jforgame.socket.share.HostAndPort;
import jforgame.socket.share.IdSession;
import jforgame.socket.share.SocketIoDispatcher;
import jforgame.socket.share.SocketIoDispatcherAdapter;
import org.forfun.mmorpg.framework.net.GameMessageFactory;
import org.forfun.mmorpg.game.ConfigScanPaths;
import org.forfun.mmorpg.game.util.JsonUtil;

/**
 * 客户端模拟器启动程序
 */
public class ClientStartup {

    public static void main(String[] args) throws Exception {

        HostAndPort hostPort = new HostAndPort();
        hostPort.setHost(ClientConfigs.REMOTE_SERVER_IP);
        hostPort.setPort(ClientConfigs.REMOTE_SERVER_PORT);


        SocketIoDispatcher msgDispatcher = new SocketIoDispatcherAdapter() {
            @Override
            public void dispatch(IdSession session, Object message) {
                System.err.println("收到消息<-- " + message.getClass().getSimpleName() + "=" + JsonUtil.object2String(message));
            }
            @Override
            public void exceptionCaught(IdSession session, Throwable cause) {
                cause.printStackTrace();
            }
        };

        SocketClient socketClient = new TcpSocketClient(msgDispatcher,new GameMessageFactory(ConfigScanPaths.MESSAGE_BASE_PATH), new StructMessageCodec(), hostPort);
        IdSession session = socketClient.openSession();

        ClientRobot robot = new ClientRobot(session);
        robot.login();
        robot.selectedPlayer(10000L);
        robot.sendGm();
    }

}
