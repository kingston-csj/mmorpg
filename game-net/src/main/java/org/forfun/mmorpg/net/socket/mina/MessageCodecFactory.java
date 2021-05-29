package org.forfun.mmorpg.net.socket.mina;

import org.forfun.mmorpg.protocol.codec.SerializerFactory;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class MessageCodecFactory implements ProtocolCodecFactory {

    private MinaProtocolDecoder decoder;

    private MinaProtocolEncoder encoder;

    public MessageCodecFactory(SerializerFactory serializerFactory) {
        this.decoder = new MinaProtocolDecoder(serializerFactory.getDecoder());
        this.encoder = new MinaProtocolEncoder(serializerFactory.getEncoder());
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return decoder;
    }

}
