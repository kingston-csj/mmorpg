import jforgame.codec.struct.StructMessageCodec
import jforgame.commons.util.JsonUtil
import jforgame.socket.client.SocketClient
import jforgame.socket.netty.client.TcpSocketClient
import jforgame.socket.share.*
import org.forfun.mmorpg.framework.net.GameMessageFactory
import org.forfun.mmorpg.game.ConfigScanPaths
import org.forfun.mmorpg.game.client.ClientConfigs
import org.forfun.mmorpg.game.client.ClientRobot


/**
 * 客户端模拟器启动程序
 */
object ClientStartup {
    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val hostPort = HostAndPort()
        hostPort.setHost(ClientConfigs.REMOTE_SERVER_IP)
        hostPort.setPort(ClientConfigs.REMOTE_SERVER_PORT)


        val msgDispatcher: SocketIoDispatcher = object : SocketIoDispatcherAdapter() {
            override fun dispatch(session: IdSession?, frame: RequestContext) {
                val message = frame.getRequest()
                System.err.println(
                    "收到消息<-- " + message.javaClass.getSimpleName() + "=" + JsonUtil.object2String(
                        message
                    )
                )
            }

            override fun exceptionCaught(session: IdSession?, cause: Throwable) {
                cause.printStackTrace()
            }
        }

        val socketClient: SocketClient = TcpSocketClient(
            msgDispatcher,
            GameMessageFactory(ConfigScanPaths.MESSAGE_BASE_PATH),
            StructMessageCodec(),
            hostPort
        )
        val session = socketClient.openSession()

        val robot = ClientRobot(session)
        robot.accountLogin()
        Thread.sleep(50)
        robot.playerLogin(10000)
        robot.sendGm()
    }
}