package org.forfun.mmorpg.framework.net;

import org.forfun.mmorpg.framework.net.mina.MinaSocketServer;
import org.forfun.mmorpg.framework.net.netty.NettySocketServer;
import org.forfun.mmorpg.net.socket.SocketServerNode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketServerAutoConfiguration {

    @Bean
    @ConditionalOnExpression(("'${game.socket.name}'.equalsIgnoreCase('mina')"))
    public MinaSocketServer createMina() {
        return new MinaSocketServer();
    }

    /**
     * 默认使用Netty
     * @return
     */
    @Bean
//    @ConditionalOnExpression(("'${game.socket.name}'.equalsIgnoreCase('netty')"))
    @ConditionalOnMissingBean(value = SocketServerNode.class)
    public NettySocketServer createNetty() {
        return new NettySocketServer();
    }

}