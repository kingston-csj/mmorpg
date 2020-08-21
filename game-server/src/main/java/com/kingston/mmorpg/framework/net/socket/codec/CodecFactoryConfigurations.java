package com.kingston.mmorpg.framework.net.socket.codec;

import com.kingston.mmorpg.framework.net.socket.json.JsonSerializerFactory;
import com.kingston.mmorpg.framework.net.socket.protobuf.ProtobufSerializerFactory;
import com.kingston.mmorpg.framework.net.socket.protostuff.ProtostuffSerializerFactory;
import com.kingston.mmorpg.framework.net.socket.serializer.ReflectSerializerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CodecFactoryConfigurations {

    @Bean
    @ConditionalOnExpression(("'${game.socket.codec}'.equalsIgnoreCase('reflect')"))
    public SerializerFactory createReflectSerializerFactory() {
        return new ReflectSerializerFactory();
    }

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

}
