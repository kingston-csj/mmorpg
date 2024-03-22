package org.forfun.mmorpg.framework.net;

import jforgame.codec.struct.StructMessageCodec;
import jforgame.socket.netty.support.server.TcpSocketServerBuilder;
import jforgame.socket.share.HostAndPort;
import jforgame.socket.share.ServerNode;
import jforgame.socket.share.message.MessageFactory;
import org.forfun.mmorpg.game.ConfigScanPaths;
import org.forfun.mmorpg.game.base.GameContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SocketServerAutoConfiguration {

//    @Bean
//    @ConditionalOnExpression(("'${game.socket.name}'.equalsIgnoreCase('mina')"))
//    public ServerNode createMina() {
//        return new MinaSocketServer();
//    }

    /**
     * 默认使用Netty
     *
     * @return
     */
    @Bean
    public ServerNode createNetty() {
        return TcpSocketServerBuilder.newBuilder()
                .bindingPort(HostAndPort.valueOf(GameContext.getServerConfig().getServerPort()))
                .setMessageFactory(GameContext.getMessageFactory())
                .setMessageCodec(new StructMessageCodec())
                .setSocketIoDispatcher(new MessageIoDispatcher())
                .build();
    }

    @Bean
    public MessageFactory createMessageFactory() {
        return new GameMessageFactory(ConfigScanPaths.MESSAGE_BASE_PATH);
    }


}