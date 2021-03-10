package org.forfun.mmorpg.client.net;

import org.forfun.mmorpg.client.IoSession;
import org.forfun.mmorpg.net.message.Message;

public class SessionManager {
	
	private static SessionManager instance = new SessionManager();
	
	private IoSession session;
	
	public static SessionManager getInstance() {
		return instance;
	}
	
	public void registerSession(IoSession session) {
		this.session = session;
	}
	
	public void sendMessage(Message request) {
		this.session.sendPacket(request);
	}

}
