package org.forfun.mmorpg.game.cross.event;

import lombok.Getter;
import lombok.Setter;
import org.forfun.mmorpg.framework.eventbus.BaseEvent;

@Getter
@Setter
public class RpcConnectedEvent implements BaseEvent {

    private int serverId;

    private int serverType;

    public static RpcConnectedEvent valueOf(int serverId, int serverType) {
        RpcConnectedEvent event = new RpcConnectedEvent();
        event.serverId = serverId;
        event.serverType = serverType;

        return event;
    }

}
