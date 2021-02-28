package com.kingston.mmorpg.framework.net;

import com.kingston.mmorpg.framework.net.socket.codec.SerializerFactory;
import com.kingston.mmorpg.framework.net.socket.codec.impl.json.JsonSerializerFactory;
import com.kingston.mmorpg.framework.net.socket.codec.impl.protobuf.ProtobufSerializerFactory;
import com.kingston.mmorpg.framework.net.socket.codec.impl.protostuff.ProtostuffSerializerFactory;
import com.kingston.mmorpg.framework.net.socket.codec.impl.reflect.ReflectSerializerFactory;
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
    @ConditionalOnMissingBean(value = SerializerFactory.class)
    public SerializerFactory createReflectSerializerFactory() {
        return new ReflectSerializerFactory();
    }

}