package org.forfun.mmorpg.game.cross.service;

import com.google.common.collect.Maps;
import org.forfun.mmorpg.net.socket.IdSession;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

@Service
public class ServerDirectoryService {

    private ConcurrentMap<Integer, IdSession> children = Maps.newConcurrentMap();

    public void registerSession(int fromSid, IdSession session) {
        children.put(fromSid, session);
    }

    public void walkAction(Consumer consumer) {
        children.values().stream().forEach(consumer);
    }
}
