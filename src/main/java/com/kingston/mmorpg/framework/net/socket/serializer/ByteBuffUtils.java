package com.kingston.mmorpg.framework.net.socket.serializer;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;

public final class ByteBuffUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ByteBuffUtils.class);
	
	public static boolean readBoolean(ByteBuf buf) {
		return buf.readByte() > 0;
	}

	public static void writeBoolean(ByteBuf buf, boolean value) {
		buf.writeByte(value ? (byte)1: (byte)0);
	}

	public static byte readByte(ByteBuf buf) {
		return buf.readByte();
	}

	public static void writeByte(ByteBuf buf, byte value) {
		buf.writeByte(value);
	}

	public static char readChar(ByteBuf buf) {
		return buf.readChar();
	}

	public static void writeChar(ByteBuf buf, char value) {
		buf.writeChar(value);
	}

	public static short readShort(ByteBuf buf) {
		return buf.readShort();
	}

	public static void writeShort(ByteBuf buf, short value) {
		buf.writeShort(value);
	}

	public static int readInt(ByteBuf buf) {
		return buf.readInt();
	}

	public static void writeInt(ByteBuf buf, int value) {
		buf.writeInt(value);
	}

	public static long readLong(ByteBuf buf) {
		return buf.readLong();
	}

	public static void writeLong(ByteBuf buf, long value) {
		buf.writeLong(value);
	}

	public static float readFloat(ByteBuf buf) {
		return buf.readFloat();
	}

	public static void writeFloat(ByteBuf buf, float value) {
		buf.writeFloat(value);
	}

	public static double readDouble(ByteBuf buf) {
		return buf.readDouble();
	}

	public static void writeDouble(ByteBuf buf, double value) {
		buf.writeDouble(value);
	}
	
	public static String readUtf8(ByteBuf buf){
		int strSize = buf.readInt();
		byte[] content = new byte[strSize];
		buf.readBytes(content);
		try {
			return new String(content,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static  void writeUtf8(ByteBuf buf,String msg){
		byte[] content ;
		try {
			if (msg == null) {
				msg = "";
			}
			content = msg.getBytes("UTF-8");
			buf.writeInt(content.length);
			buf.writeBytes(content);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
