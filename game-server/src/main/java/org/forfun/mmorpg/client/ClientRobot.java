package org.forfun.mmorpg.client;

import jforgame.socket.share.IdSession;
import org.forfun.mmorpg.game.gm.message.ReqGmCommand;
import org.forfun.mmorpg.game.player.message.ReqAccountLogin;
import org.forfun.mmorpg.game.player.message.ReqCreateNewPlayer;
import org.forfun.mmorpg.game.player.message.ReqSelectPlayer;

public class ClientRobot {

    public IdSession session;

    public ClientRobot(IdSession session) {
        this.session = session;
    }

    public void sendGm() {
        ReqGmCommand req = new ReqGmCommand();
        req.setParams("level 99");
        session.send(req);
    }

    public void createNew() {
        ReqCreateNewPlayer req = new ReqCreateNewPlayer();
        req.setName("Happy");
        session.send(req);
    }

    public void login() {
        ReqAccountLogin req = new ReqAccountLogin();
        req.setPassword("forfun");
        req.setAccountId(123L);
        session.send(req);
    }

    public void selectedPlayer(long playerId) {
        ReqSelectPlayer req = new ReqSelectPlayer();
        req.setPlayerId(playerId);
        session.send(req);
    }
}
