package org.forfun.mmorpg.net.dispatcher;

import org.forfun.mmorpg.net.message.Message;
import org.forfun.mmorpg.net.socket.IdSession;

public interface IMessageDispatcher {

    void handle(IdSession session, Message message);
}
