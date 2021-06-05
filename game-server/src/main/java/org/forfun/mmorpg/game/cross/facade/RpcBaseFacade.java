package org.forfun.mmorpg.game.cross.facade;

import org.forfun.mmorpg.game.CrossConfig;
import org.forfun.mmorpg.game.Modules;
import org.forfun.mmorpg.game.cross.message.RpcReqHello;
import org.forfun.mmorpg.game.cross.message.RpcReqServerLogin;
import org.forfun.mmorpg.game.cross.message.RpcRespHello;
import org.forfun.mmorpg.game.cross.service.ServerDirectoryService;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.protocol.annotation.ModuleMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@ModuleMeta(module = Modules.CROSS)
public class RpcBaseFacade {

    @Autowired
    CrossConfig crossConfig;

    @Autowired
    ServerDirectoryService directoryService;

    public void sayHello(IdSession session, RpcReqHello request) {
        RpcRespHello response = new RpcRespHello();
        response.setIndex(request.getIndex());
        response.setContent("hello world");
        session.sendPacket(response);
    }

    public void loginServer(IdSession session, RpcReqServerLogin request) {
        String md5 = request.getFromSid() + "_" + crossConfig.getSign();
        if (md5.equals(request.getSign())) {
            LoggerUtils.error("服务器[{}]登录失败，md5验证不通过", request.getFromSid());
            return;
        }
        directoryService.registerSession(request.getFromSid(), session);
    }

}
