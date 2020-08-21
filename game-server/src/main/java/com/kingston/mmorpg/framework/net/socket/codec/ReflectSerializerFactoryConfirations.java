package com.kingston.mmorpg.framework.net.socket.codec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReflectSerializerFactoryConfirations {

    @Bean
    public SerializerFactory createSerializerFactory() {
        return new ReflectSerializerFactory();
    }
}
