package org.forfun.mmorpg.game.monitor.jmx

import jforgame.commons.util.FileUtil
import javax.management.JMX
import javax.management.MBeanServerConnection
import javax.management.ObjectName
import javax.management.remote.JMXConnector
import javax.management.remote.JMXConnectorFactory
import javax.management.remote.JMXServiceURL

object JmxClient {

    /**
     * 该工具类简直无敌，试想一下，在生产环境，直接用一个ide就可以链接服务器的jmx接口， 从而可以查看服务器的内存数据，或者执行任意代码，
     * 想想都鸡动不已 ^_^
     */
    @JvmStatic
    fun main(args: Array<String>) {
        // 如果执行的groovy脚本内容过长
        // 则可以把脚本写在一个文件里，然后使用jmx client 动态调用mbean接口方法
        val user = "root"
        val pwd = "root"

        // 如果生产环境需要账号验证的话
        val account = arrayOf(user, pwd)
        val props = HashMap<String, Array<String>>()
        props["jmx.remote.credentials"] = account

        // 见启动脚本的vm参数，
        // -Dcom.sun.management.jmxremote.port=9090
        // -Dcom.sun.management.jmxremote.authenticate=false
        // -Dcom.sun.management.jmxremote.ssl=false
        val address = JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9090/jmxrmi")
        val connector = JMXConnectorFactory.connect(address, props)
        val mBeanConnection = connector.getMBeanServerConnection()

        connector.connect()
        val objectName = ObjectName(
            "org.forfun.mmorpg.game.monitor.jmx:name=gameMonitor,type=GameMonitor"
        )
        val mBean = JMX.newMBeanProxy(mBeanConnection, objectName, GameMonitorMBean::class.java)

        val script = FileUtil.readFullText("hotswap/groovy.txt")
        System.err.println("script:\n$script")

        System.err.println(mBean.execGroovyScript(script))
    }
}
