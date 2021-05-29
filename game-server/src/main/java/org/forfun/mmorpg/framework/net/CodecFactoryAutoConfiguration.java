package org.forfun.mmorpg.framework.net;

import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.forfun.mmorpg.protocol.codec.impl.json.JsonSerializerFactory;
import org.forfun.mmorpg.protocol.codec.impl.protobuf.ProtobufSerializerFactory;
import org.forfun.mmorpg.protocol.codec.impl.protostuff.ProtostuffSerializerFactory;
import org.forfun.mmorpg.protocol.codec.impl.reflect.ReflectSerializerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodecFactoryAutoConfiguration {

    @Bean
    @ConditionalOnExpression(("'${game.socket.codec}'.equalsIgnoreCase('protobuf')"))
    public SerializerFactory createProtobufSerializerFactory() {
        return new ProtobufSerializerFactory();
    }

    @Bean
    @ConditionalOnExpression(("'${game.socket.codec}'.equalsIgnoreCase('json')"))
    public SerializerFactory createJsonSerializerFactory() {
        return new JsonSerializerFactory();
    }

    @Bean
    @ConditionalOnExpression(("'${game.socket.codec}'.equalsIgnoreCase('protostuff')"))
    public SerializerFactory createProtostuffSerializerFactory() {
        return new ProtostuffSerializerFactory();
    }

    /**
     * 默认基于反射机制
     * @return
     */
    @Bean
//    @ConditionalOnExpression(("'${game.socket.codec}'.equalsIgnoreCase('reflect')"))
    @ConditionalOnMissingBean()
    public SerializerFactory createReflectSerializerFactory() {
        return new ReflectSerializerFactory();
    }

}