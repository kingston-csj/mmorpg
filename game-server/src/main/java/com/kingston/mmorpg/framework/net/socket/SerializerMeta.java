package com.kingston.mmorpg.framework.net.socket;

import com.kingston.mmorpg.framework.net.socket.serializer.Serializer;

public class SerializerMeta {
	
	private Serializer serializer;
	
	private Class<?> clazz;
	
	private int id;
	
	public SerializerMeta(Serializer serializer, Class<?> clazz, int id) {
		super();
		this.serializer = serializer;
		this.clazz = clazz;
		this.id = id;
	}

	public Serializer getSerializer() {
		return serializer;
	}

	public void setSerializer(Serializer serializer) {
		this.serializer = serializer;
	}

	public Class<?> getType() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
