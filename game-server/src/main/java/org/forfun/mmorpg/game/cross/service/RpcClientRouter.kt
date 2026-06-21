package org.forfun.mmorpg.game.cross.service

import jforgame.codec.struct.StructMessageCodec
import jforgame.commons.util.JsonUtil
import jforgame.socket.client.SocketClient
import jforgame.socket.netty.client.TcpSocketClient
import jforgame.socket.share.HostAndPort
import jforgame.socket.share.IdSession
import jforgame.socket.share.RequestContext
import jforgame.socket.share.SocketIoDispatcher
import jforgame.socket.share.SocketIoDispatcherAdapter
import org.apache.commons.codec.digest.Md5Crypt
import org.forfun.mmorpg.game.CrossConfig
import org.forfun.mmorpg.game.ServerConfig
import org.forfun.mmorpg.game.base.GameContext
import org.forfun.mmorpg.game.cross.message.RpcReqServerLogin
import org.forfun.mmorpg.game.cross.message.RpcServerNode
import org.forfun.mmorpg.game.cross.router.BalanceStrategy
import org.forfun.mmorpg.game.cross.router.RoundBalanceStrategy
import org.forfun.mmorpg.game.logger.LoggerUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

@Service
open class RpcClientRouter {

    private val servers = ConcurrentHashMap<Int, IdSession>()
    private val nodes = ConcurrentHashMap<Int, RpcServerNode>()

    private val fightBalanceStrategy: BalanceStrategy = RoundBalanceStrategy()

    @Autowired
    private lateinit var crossConfig: CrossConfig

    fun getSession(targetSid: Int): IdSession? {
        return servers[targetSid]
    }

    fun checkAndRegisterConnections() {
        listRpcNodes().forEach { node ->
            if (!isSessionCreated(node.sid)) {
                createSession(node.sid)
            }
        }
    }

    private fun createSession(targetSid: Int): IdSession? {
        if (servers.containsKey(targetSid)) {
            return servers[targetSid]
        }
        val msgDispatcher: SocketIoDispatcher = object : SocketIoDispatcherAdapter() {
            override fun dispatch(session: IdSession, requestContext: RequestContext) {
                System.err.println(
                    "收到消息<-- " + requestContext.request.javaClass.simpleName + "=" + JsonUtil.object2String(
                        requestContext.request
                    )
                )
            }

            override fun exceptionCaught(session: IdSession, cause: Throwable) {
                cause.printStackTrace()
            }
        }
        synchronized(this) {
            if (!servers.containsKey(targetSid)) {
                val socketClient = TcpSocketClient(
                    msgDispatcher,
                    GameContext.getMessageFactory(),
                    StructMessageCodec(),
                    HostAndPort.valueOf(crossConfig.centerIp, crossConfig.centerPort)
                )
                try {
                    val session = socketClient.openSession()
                    val reqServerLogin = buildServerLoginRequest()
                    session.send(reqServerLogin)
                } catch (e: IOException) {
                    LoggerUtils.error("", e)
                    return null
                }
            }
            return servers[targetSid]
        }
    }

    fun isSessionCreated(targetSid: Int): Boolean {
        return servers.containsKey(targetSid) && servers[targetSid] != null
    }

    fun listRpcNodes(): List<RpcServerNode> {
        return ArrayList(nodes.values)
    }

    fun listRpcNodes(serverType: Int): List<RpcServerNode> {
        return nodes.values.filter { it.type == serverType }
    }

    fun registerSession(targetSid: Int, session: IdSession) {
        servers[targetSid] = session
    }

    fun registerRpcNode(node: RpcServerNode) {
        nodes[node.sid] = node
    }

    private fun buildServerLoginRequest(): RpcReqServerLogin {
        val login = RpcReqServerLogin()
        val serverConfig = GameContext.getServerConfig()
        login.serverType = GameContext.serverType!!.type
        login.fromSid = serverConfig!!.serverId
        val sign = GameContext.getBean(CrossConfig::class.java).sign
        val md5 = Md5Crypt.apr1Crypt(serverConfig.serverId.toString() + "_" + sign)
        login.sign = md5
        return login
    }

    val centerSession: IdSession?
        get() = getSession(crossConfig.centerId)

    /**
     * choose next stateless fight server based on [BalanceStrategy] BalanceStrategy
     * @return
     */
    fun nextFightSession(): IdSession? {
        val choose = fightBalanceStrategy.next(servers.keys.stream().collect(Collectors.toList()))
        return if (choose <= 0) null else getSession(choose)
    }
}
