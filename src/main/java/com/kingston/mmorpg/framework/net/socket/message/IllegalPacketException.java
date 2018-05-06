package com.kingston.mmorpg.framework.net.socket.message;

public class IllegalPacketException  extends RuntimeException{


	public IllegalPacketException() {
		super();
	}

	public IllegalPacketException(String message) {
		super(message);
	}
}
