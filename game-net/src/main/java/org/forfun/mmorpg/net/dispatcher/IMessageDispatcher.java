package org.forfun.mmorpg.net.dispatcher;

import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.protocol.Message;

public interface IMessageDispatcher {

    void onSessionCreated(IdSession session);

    /**
     * message entrance, in which io thread dispatch messages
     *
     * @param session
     * @param message
     */
    void dispatch(IdSession session, Message message);

    void onSessionClosed(IdSession session);
}
