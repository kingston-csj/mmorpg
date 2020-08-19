package com.kingston.mmorpg.framework.net;

import com.kingston.mmorpg.framework.net.socket.mina.MinaSocketServer;
import com.kingston.mmorpg.framework.net.socket.netty.NettySocketServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketServerConfigurations {

    @Bean
    @ConditionalOnExpression(("'${game.socket.name}'.equals('netty')"))
    public NettySocketServer createNetty() {
        return new NettySocketServer();
    }

    @Bean
    @ConditionalOnExpression(("'${game.socket.name}'.equals('mina')"))
    public MinaSocketServer createMina() {
        return new MinaSocketServer();
    }
    
}