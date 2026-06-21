package org.forfun.mmorpg.game.cross.facade

import jakarta.annotation.PostConstruct
import jforgame.socket.share.IdSession
import jforgame.socket.share.annotation.MessageRoute
import org.forfun.mmorpg.game.CrossConfig
import org.forfun.mmorpg.game.Modules
import org.forfun.mmorpg.game.ServerLayer
import org.forfun.mmorpg.game.ServerType
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.cross.constant.ConstantCross
import org.forfun.mmorpg.game.cross.event.RpcConnectedEvent
import org.forfun.mmorpg.game.cross.message.*
import org.forfun.mmorpg.game.cross.service.RpcClientRouter
import org.forfun.mmorpg.game.logger.LoggerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Controller

@Controller
@MessageRoute(module = Modules.CROSS)
open class RpcBaseFacade {

    @Autowired
    private lateinit var crossConfig: CrossConfig

    @Autowired
    private lateinit var clientRouter: RpcClientRouter

    @PostConstruct
    private fun init() {
        if (GameContext.serverType != ServerType.CENTRE) {
            val centerId = crossConfig.centerId
            val node = RpcServerNode()
            node.sid = centerId
            node.ip = crossConfig.centerIp
            node.port = crossConfig.centerPort
            node.type = ServerType.CENTRE.type
            clientRouter.registerRpcNode(node)
        }
    }

    /**
     * check rpc connection per period
     */
    @Scheduled(cron = "0/30 * * * * ? ")
    fun checkConnection() {
//        clientRouter.checkAndRegisterConnections()
    }

    /**
     * check rpc connection per period
     */
    @Scheduled(cron = "0/59 * * * * ? ")
    fun checkDirectory() {
        if (GameContext.serverType == ServerType.GAME) {
            val centerSession = GameContext.getBean(RpcClientRouter::class.java).centerSession
            if (centerSession != null) {
                centerSession.send(Rpc_G2C_FetchFightServerNodes())
            }
        }
    }

    /**
     * 目标服--验证登录
     * @param session
     * @param request
     */
    fun reqLoginServer(session: IdSession, request: RpcReqServerLogin) {
        val md5 = request.fromSid.toString() + "_" + crossConfig.sign
        if (md5 == request.sign) {
            LoggerUtils.error("服务器[${request.fromSid}]登录失败，md5验证不通过")
            session.close()
            return
        }

        clientRouter.registerSession(request.fromSid, session)
        session.setAttribute(ConstantCross.RPC_SOURCE_SERVER, request.fromSid)
        val resp = RpcRespServerLogin()
        resp.remoteSid = GameContext.getServerConfig()!!.serverId
        session.send(resp)
    }

    /**
     * 请求方--登录回调
     * @param session
     * @param response
     */
    fun respLoginServer(session: IdSession, response: RpcRespServerLogin) {
        clientRouter.registerSession(response.remoteSid, session)
        GameContext.getEventBus()!!.asyncPost(RpcConnectedEvent.valueOf(response.remoteSid, response.serverType))

        GameContext.getBean(ServerLayer::class.java).onCenterServerConnected()
    }

    /**
     * 游戏服 -> 中心服
     * 请求战斗服列表
     * @param session
     * @param request
     */
    fun reqLoginServer(session: IdSession, request: Rpc_G2C_FetchFightServerNodes) {
        val fightNodes = clientRouter.listRpcNodes(ServerType.FIGHT.type)
        val response = Rpc_C2G_FetchFightServerNodes()
        response.fightNodes = fightNodes
        session.send(response)
    }

    /**
     * 中心服 -> 游戏服
     * 推送全量战斗服列表
     * @param session
     * @param response
     */
    fun respLoginServer(session: IdSession, response: Rpc_C2G_FetchFightServerNodes) {
        response.fightNodes?.forEach { clientRouter.registerRpcNode(it) }
    }
}
