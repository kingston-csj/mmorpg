package org.forfun.mmorpg.game.cross.service;

import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.Md5Crypt;
import org.forfun.mmorpg.game.CrossConfig;
import org.forfun.mmorpg.game.ServerConfig;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.cross.message.RpcReqServerLogin;
import org.forfun.mmorpg.game.cross.message.RpcServerNode;
import org.forfun.mmorpg.game.cross.router.BalanceStrategy;
import org.forfun.mmorpg.game.cross.router.RobinBalanceStrategy;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.net.HostPort;
import org.forfun.mmorpg.net.client.RpcClientFactory;
import org.forfun.mmorpg.net.dispatcher.IMessageDispatcher;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
public class RpcClientRouter {

    private ConcurrentMap<Integer, IdSession> servers = Maps.newConcurrentMap();

    private ConcurrentMap<Integer, RpcServerNode> nodes = Maps.newConcurrentMap();

    private BalanceStrategy fightBalanceStrategy = new RobinBalanceStrategy();

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
        synchronized (this) {
            if (!servers.containsKey(targetSid)) {
                IMessageDispatcher messageDispatcher = GameContext.getMessageDispatcher();
                SerializerFactory serializerFactory = GameContext.getMessageSerializer();
                RpcClientFactory clientFactory = new RpcClientFactory(messageDispatcher, serializerFactory);
                HostPort hostPort = new HostPort();
                hostPort.setHost(crossConfig.getCenterIp());
                hostPort.setPort(crossConfig.getCenterPort());
                try {
                    IdSession session = clientFactory.createSession(hostPort);
                    RpcReqServerLogin reqServerLogin = buildServerLoginRequest();
                    session.sendPacket(reqServerLogin);
                } catch (Exception e) {
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
        return servers.get(targetSid).isValid();
    }

    public List<RpcServerNode> listRpcNodes() {
        return nodes.values().stream().collect(Collectors.toList());
    }

    public void registerSession(int targetSid, IdSession session) {
        servers.put(targetSid, session);
    }

    public void registerRpcNode(int serverId, RpcServerNode node) {
        nodes.put(serverId, node);
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
