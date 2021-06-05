package org.forfun.mmorpg.game.cross.facade;

import org.forfun.mmorpg.game.Modules;
import org.forfun.mmorpg.game.cross.message.RpcReqHello;
import org.forfun.mmorpg.game.cross.message.RpcReqServerLogin;
import org.forfun.mmorpg.game.cross.message.RpcRespHello;
import org.forfun.mmorpg.game.cross.service.ServerDirectoryService;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.protocol.annotation.ModuleMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ModuleMeta(module = Modules.CROSS)
public class RpcBaseFacade {

    @Autowired
    ServerDirectoryService directoryService;

    public void sayHello(IdSession session, RpcReqHello request) {
        RpcRespHello response = new RpcRespHello();
        response.setIndex(request.getIndex());
        session.sendPacket(response);
    }

    public void loginServer(IdSession session, RpcReqServerLogin request) {
        directoryService.registerSession(request.getFromSid(), session);
    }

}
