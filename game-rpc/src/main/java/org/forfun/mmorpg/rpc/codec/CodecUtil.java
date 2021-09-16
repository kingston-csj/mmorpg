package org.forfun.mmorpg.rpc.codec;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class CodecUtil {

    public static String readUtf8(ByteBuf buf) {
        int strSize = buf.readInt();
        byte[] content = new byte[strSize];
        buf.readBytes(content);
        try {
            return new String(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static void writeUtf8(ByteBuf buf, String msg) {
        byte[] content;
        try {
            if (msg == null) {
                msg = "";
            }
            content = msg.getBytes("UTF-8");
            buf.writeInt(content.length);
            buf.writeBytes(content);
        } catch (UnsupportedEncodingException e) {
        }
    }
}
