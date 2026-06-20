package org.forfun.mmorpg.game.cross.event;

import jforgame.commons.eventbus.BaseEvent;

public class RpcConnectedEvent implements BaseEvent {

    private int serverId;

    private int serverType;

    public static RpcConnectedEvent valueOf(int serverId, int serverType) {
        RpcConnectedEvent event = new RpcConnectedEvent();
        event.serverId = serverId;
        event.serverType = serverType;

        return event;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getServerType() {
        return serverType;
    }

    public void setServerType(int serverType) {
        this.serverType = serverType;
    }

}
