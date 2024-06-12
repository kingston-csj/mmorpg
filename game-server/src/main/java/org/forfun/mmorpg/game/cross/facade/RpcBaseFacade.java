package org.forfun.mmorpg.game.cross.facade;

import jakarta.annotation.PostConstruct;
import jforgame.socket.share.IdSession;
import jforgame.socket.share.annotation.MessageRoute;
import org.forfun.mmorpg.framework.eventbus.EventBus;
import org.forfun.mmorpg.game.CrossConfig;
import org.forfun.mmorpg.game.Modules;
import org.forfun.mmorpg.game.ServerLayer;
import org.forfun.mmorpg.game.ServerType;
import org.forfun.mmorpg.game.base.GameContext;
import org.forfun.mmorpg.game.cross.constant.ConstantCross;
import org.forfun.mmorpg.game.cross.event.RpcConnectedEvent;
import org.forfun.mmorpg.game.cross.message.*;
import org.forfun.mmorpg.game.cross.service.RpcClientRouter;
import org.forfun.mmorpg.game.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@MessageRoute(module = Modules.CROSS)
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
            clientRouter.registerRpcNode(node);
        }
    }

    /**
     * check rpc connection per period
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    public void checkConnection() {
//        clientRouter.checkAndRegisterConnections();
    }

    /**
     * check rpc connection per period
     */
    @Scheduled(cron = "0/59 * * * * ? ")
    public void checkDirectory() {
        if (GameContext.serverType == ServerType.GAME) {
            IdSession centerSession = GameContext.getBean(RpcClientRouter.class).getCenterSession();
            if (centerSession != null) {
                centerSession.send(new Rpc_G2C_FetchFightServerNodes());
            }
        }
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
            try {
                session.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        clientRouter.registerSession(request.getFromSid(), session);
        session.setAttribute(ConstantCross.RPC_SOURCE_SERVER, request.getFromSid());
        RpcRespServerLogin resp = new RpcRespServerLogin();
        resp.setRemoteSid(GameContext.getServerConfig().getServerId());
        session.send(resp);
    }

    /**
     * 请求方--登录回调
     * @param session
     * @param response
     */
    public void respLoginServer(IdSession session, RpcRespServerLogin response) {
        clientRouter.registerSession(response.getRemoteSid(), session);
        EventBus.getInstance().asyncPost(RpcConnectedEvent.valueOf(response.getRemoteSid(), response.getServerType()));

        GameContext.getBean(ServerLayer.class).onCenterServerConnected();
    }


    /**
     * 游戏服 -> 中心服
     * 请求战斗服列表
     * @param session
     * @param request
     */
    public void reqLoginServer(IdSession session, Rpc_G2C_FetchFightServerNodes request) {
        List<RpcServerNode> fightNodes = clientRouter.listRpcNodes(ServerType.FIGHT.type);
        Rpc_C2G_FetchFightServerNodes response = new Rpc_C2G_FetchFightServerNodes();
        response.setFightNodes(fightNodes);
        session.send(response);
    }

    /**
     * 中心服 -> 游戏服
     * 推送全量战斗服列表
     * @param session
     * @param response
     */
    public void respLoginServer(IdSession session, Rpc_C2G_FetchFightServerNodes response) {
        List<RpcServerNode> fightNodes = response.getFightNodes();
        fightNodes.forEach(clientRouter::registerRpcNode);
    }

}
