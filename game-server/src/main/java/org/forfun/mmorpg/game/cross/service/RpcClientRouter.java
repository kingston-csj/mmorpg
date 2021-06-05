package org.forfun.mmorpg.game.cross.service;

import com.google.common.collect.Maps;
import org.forfun.mmorpg.game.CrossConfig;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.net.HostPort;
import org.forfun.mmorpg.net.client.RpcClientFactory;
import org.forfun.mmorpg.net.dispatcher.IMessageDispatcher;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;

@Service
public class RpcClientRouter {

    private ConcurrentMap<Integer, IdSession> servers = Maps.newConcurrentMap();

    @Autowired
    CrossConfig crossConfig;

    public IdSession getSession(int targetSid) {
        if (servers.containsKey(targetSid)) {
            return servers.get(targetSid);
        }
        synchronized (this) {
            IMessageDispatcher messageDispatcher = GameContext.getMessageDispatcher();
            SerializerFactory serializerFactory = GameContext.getMessageSerializer();
            RpcClientFactory clientFactory = new RpcClientFactory(messageDispatcher, serializerFactory);
            HostPort hostPort = new HostPort();
            hostPort.setHost(crossConfig.getCenterIp());
            hostPort.setPort(crossConfig.getCenterPort());
            try {
                IdSession session = clientFactory.createSession(hostPort);
                return session;
            } catch (Exception e) {
                LoggerUtils.error("", e);
                return null;
            }
        }
    }

    public IdSession getCenterSession() {
        return getSession(crossConfig.getCenterId());
    }

}
