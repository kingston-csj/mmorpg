package org.forfun.mmorpg.client;

import org.forfun.mmorpg.game.gm.message.ReqGmCommand;
import org.forfun.mmorpg.game.player.message.ReqAccountLogin;
import org.forfun.mmorpg.game.player.message.ReqCreateNewPlayer;
import org.forfun.mmorpg.game.player.message.ReqSelectPlayer;
import org.forfun.mmorpg.net.socket.IdSession;

public class ClientRobot {

    public IdSession session;

    public ClientRobot(IdSession session) {
        this.session = session;
    }

    public void sendGm() {
        ReqGmCommand req = new ReqGmCommand();
        req.setParams("level 99");
        session.sendPacket(req);
    }

    public void createNew() {
        ReqCreateNewPlayer req = new ReqCreateNewPlayer();
        req.setName("Happy");
        session.sendPacket(req);
    }

    public void login() {
        ReqAccountLogin req = new ReqAccountLogin();
        req.setPassword("forfun");
        req.setAccountId(123L);
        session.sendPacket(req);
    }

    public void selectedPlayer(long playerId) {
        ReqSelectPlayer req = new ReqSelectPlayer();
        req.setPlayerId(playerId);
        session.sendPacket(req);
    }
}
