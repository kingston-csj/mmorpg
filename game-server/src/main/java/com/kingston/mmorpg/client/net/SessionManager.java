package com.kingston.mmorpg.client.net;

import com.kingston.mmorpg.client.IoSession;
import com.kingston.mmorpg.net.message.Message;

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
