package org.forfun.mmorpg.game.cross.facade;

import org.forfun.mmorpg.framework.eventbus.EventBus;
import org.forfun.mmorpg.game.CrossConfig;
import org.forfun.mmorpg.game.Modules;
import org.forfun.mmorpg.game.ServerType;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.game.cross.event.RpcConnectedEvent;
import org.forfun.mmorpg.game.cross.message.RpcReqHello;
import org.forfun.mmorpg.game.cross.message.RpcReqServerLogin;
import org.forfun.mmorpg.game.cross.message.RpcRespHello;
import org.forfun.mmorpg.game.cross.message.RpcRespServerLogin;
import org.forfun.mmorpg.game.cross.message.RpcServerNode;
import org.forfun.mmorpg.game.cross.service.HelloService;
import org.forfun.mmorpg.game.cross.service.RpcClientRouter;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.forfun.mmorpg.net.socket.IdSession;
import org.forfun.mmorpg.net.socket.SessionCloseReason;
import org.forfun.mmorpg.protocol.annotation.ModuleMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
@ModuleMeta(module = Modules.CROSS)
public class RpcBaseFacade {

    @Autowired
    CrossConfig crossConfig;

    @Autowired
    private RpcClientRouter clientRouter;

    @PostConstruct
    private void init() {
        if (GameContext.serverType != ServerType.CENTRE) {
            int centerId = crossConfig.getCenterId();
            RpcServerNode node = new RpcServerNode();
            node.setSid(centerId);
            node.setIp(crossConfig.getCenterIp());
            node.setPort(crossConfig.getCenterPort());
            node.setType(ServerType.CENTRE.type);
            clientRouter.registerRpcNode(centerId, node);
        }
    }

    /**
     * check rpc connection per period
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void tickCheck() {
        clientRouter.checkAndRegisterConnections();
    }


    public void sayHello(IdSession session, RpcReqHello request) {
        RpcRespHello response = new RpcRespHello();
        response.setCallbackId(request.getCallbackId());
        response.setContent("hello world");
        session.sendPacket(response);
    }

    /**
     * 目标服--验证登录
     * @param session
     * @param request
     */
    public void reqLoginServer(IdSession session, RpcReqServerLogin request) {
        String md5 = request.getFromSid() + "_" + crossConfig.getSign();
        if (md5.equals(request.getSign())) {
            LoggerUtils.error("服务器[{}]登录失败，md5验证不通过", request.getFromSid());
            session.close(SessionCloseReason.ILLEGAL_LOGIN);
            return;
        }

        clientRouter.registerSession(request.getFromSid(), session);
        session.setAttribute(ConstantCross.RPC_SOURCE_SERVER, request.getFromSid());
        RpcRespServerLogin resp = new RpcRespServerLogin();
        resp.setRemoteSid(GameContext.getServerConfig().getServerId());
        session.sendPacket(resp);
    }

    /**
     * 请求方--登录回调
     * @param session
     * @param response
     */
    public void respLoginServer(IdSession session, RpcRespServerLogin response) {
        clientRouter.registerSession(response.getRemoteSid(), session);
        GameContext.getBean(HelloService.class).sayHello();
        EventBus.getInstance().asyncPost(RpcConnectedEvent.valueOf(response.getRemoteSid(), response.getServerType()));
    }

}
