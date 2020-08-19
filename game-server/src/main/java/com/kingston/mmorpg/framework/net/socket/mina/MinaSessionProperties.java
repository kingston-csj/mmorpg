package com.kingston.mmorpg.framework.net.socket.mina;

import org.apache.mina.core.session.AttributeKey;

public interface MinaSessionProperties {


    AttributeKey UserSession = new AttributeKey(MinaSessionProperties.class, "GameSession");
}
