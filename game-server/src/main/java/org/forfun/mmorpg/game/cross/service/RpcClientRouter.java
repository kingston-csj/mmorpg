package org.forfun.mmorpg.game.cross.service;

import jforgame.codec.struct.StructMessageCodec;
import jforgame.socket.client.SocketClient;
import jforgame.socket.netty.support.client.TcpSocketClient;
import jforgame.socket.share.HostAndPort;
import jforgame.socket.share.IdSession;
import jforgame.socket.share.SocketIoDispatcher;
import jforgame.socket.share.SocketIoDispatcherAdapter;
import org.apache.commons.codec.digest.Md5Crypt;
import org.forfun.mmorpg.game.CrossConfig;
import org.forfun.mmorpg.game.ServerConfig;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.cross.message.RpcReqServerLogin;
import org.forfun.mmorpg.game.cross.message.RpcServerNode;
import org.forfun.mmorpg.game.cross.router.BalanceStrategy;
import org.forfun.mmorpg.game.cross.router.RoundBalanceStrategy;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.game.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class RpcClientRouter {

    private ConcurrentMap<Integer, IdSession> servers = new ConcurrentHashMap<>();

    private ConcurrentMap<Integer, RpcServerNode> nodes = new ConcurrentHashMap<>();

    private BalanceStrategy fightBalanceStrategy = new RoundBalanceStrategy();

    @Autowired
    private CrossConfig crossConfig;

    public IdSession getSession(int targetSid) {
        return servers.get(targetSid);
    }

    public void checkAndRegisterConnections() {
        listRpcNodes().forEach(node -> {
            if (!isSessionCreated(node.getSid())) {
                createSession(node.getSid());
            }
        });
    }

    private IdSession createSession(int targetSid) {
        if (servers.containsKey(targetSid)) {
            return servers.get(targetSid);
        }
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
        synchronized (this) {
            if (!servers.containsKey(targetSid)) {
                SocketClient socketClient = new TcpSocketClient(msgDispatcher, GameContext.getMessageFactory(), new StructMessageCodec(),
                        HostAndPort.valueOf(crossConfig.getCenterIp(),crossConfig.getCenterPort()));
                try {
                    IdSession session = socketClient.openSession();
                    RpcReqServerLogin reqServerLogin = buildServerLoginRequest();
                    session.send(reqServerLogin);
                } catch (IOException e) {
                    LoggerUtils.error("", e);
                    return null;
                }
            }
            return servers.get(targetSid);
        }
    }

    public boolean isSessionCreated(int targetSid) {
        if (!servers.containsKey(targetSid)) {
            return false;
        }
        return servers.get(targetSid) != null;
    }

    public List<RpcServerNode> listRpcNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<RpcServerNode> listRpcNodes(int serverType) {
        return nodes.values().stream().filter(rpcServerNode -> rpcServerNode.getType() == serverType).collect(Collectors.toList());
    }

    public void registerSession(int targetSid, IdSession session) {
        servers.put(targetSid, session);
    }

    public void registerRpcNode(RpcServerNode node) {
        nodes.put(node.getSid(), node);
    }

    private RpcReqServerLogin buildServerLoginRequest() {
        RpcReqServerLogin login = new RpcReqServerLogin();
        ServerConfig serverConfig = GameContext.getServerConfig();
        login.setServerType(GameContext.serverType.type);
        login.setFromSid(serverConfig.getServerId());
        String sign = GameContext.getBean(CrossConfig.class).getSign();
        String md5 = Md5Crypt.apr1Crypt(serverConfig.getServerId() + "_" + sign);
        login.setSign(md5);
        return login;
    }

    public IdSession getCenterSession() {
        return getSession(crossConfig.getCenterId());
    }

    /**
     * choose next stateless fight server based on {@link BalanceStrategy} BalanceStrategy
     *
     * @return
     */
    public IdSession nextFightSession() {
        int choose = fightBalanceStrategy.next(servers.keySet().stream().collect(Collectors.toList()));
        if (choose <= 0) {
            return null;
        }
        return getSession(choose);
    }

}
