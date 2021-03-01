package com.kingston.mmorpg.net.socket.codec.impl.reflect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;

public class ArrayCodec extends Codec {

    @Override
    public Object decode(ByteBuffer in, Class<?> type, Class<?> wrapper) {
        int size = ByteBuffUtil.readShort(in);

        Object array = ReflectUtil.newArray(type, wrapper, size);

        for (int i = 0; i < size; i++) {
            Codec fieldCodec = getSerializer(wrapper);
            Object eleValue = fieldCodec.decode(in, wrapper, null);
            Array.set(array, i, eleValue);
        }

        return array;
    }

    @Override
    public void encode(ByteBuffer out, Object value, Class<?> wrapper) {
        if (value == null) {
            ByteBuffUtil.writeShort(out, (short) 0);
            return;
        }
        int size = Array.getLength(value);
        ByteBuffUtil.writeShort(out, (short) size);
        for (int i = 0; i < size; i++) {
            Object elem = Array.get(value, i);
            Class<?> clazz = elem.getClass();
            Codec fieldCodec = getSerializer(clazz);
            fieldCodec.encode(out, elem, wrapper);
        }
    }

    public static class ByteBuffUtil {

        private static final Logger logger = LoggerFactory.getLogger(ByteBuffUtil.class);

        public static boolean readBoolean(ByteBuffer buf) {
            return buf.get() > 0;
        }

        public static void writeBoolean(ByteBuffer buf, boolean value) {
            buf.put(value ? (byte)1: (byte)0);
        }

        public static byte readByte(ByteBuffer buf) {
            return buf.get();
        }

        public static void writeByte(ByteBuffer buf, byte value) {
            buf.put(value);
        }

        public static char readChar(ByteBuffer buf) {
            return buf.getChar();
        }

        public static void writeChar(ByteBuffer buf, char value) {
            buf.putChar(value);
        }

        public static short readShort(ByteBuffer buf) {
            return buf.getShort();
        }

        public static void writeShort(ByteBuffer buf, short value) {
            buf.putShort(value);
        }

        public static int readInt(ByteBuffer buf) {
            // TODO int变长压缩？？！！
            return buf.getInt();
        }

        public static void writeInt(ByteBuffer buf, int value) {
            buf.putInt(value);
        }

        public static long readLong(ByteBuffer buf) {
            return buf.getLong();
        }

        public static void writeLong(ByteBuffer buf, long value) {
            buf.putLong(value);
        }

        public static float readFloat(ByteBuffer buf) {
            return buf.getFloat();
        }

        public static void writeFloat(ByteBuffer buf, float value) {
            buf.putFloat(value);
        }

        public static double readDouble(ByteBuffer buf) {
            return buf.getDouble();
        }

        public static void writeDouble(ByteBuffer buf, double value) {
            buf.putDouble(value);
        }


        public static String readUtf8(ByteBuffer buf) {
            int strSize = buf.getInt();
            byte[] content = new byte[strSize];
            buf.get(content);
            try {
                return new String(content,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("", e);
                return "";
            }
        }

        public static void writeUtf8(ByteBuffer buf, String msg) {
            byte[] content ;
            try {
                if (msg == null) {
                    msg = "";
                }
                content = msg.getBytes("UTF-8");
                buf.putInt(content.length);
                buf.put(content);
            } catch (UnsupportedEncodingException e) {
                logger.error("", e);
            }
        }

    }

    public static class SerializerMeta {

        private Codec codec;

        private Class<?> clazz;

        private int id;

        public SerializerMeta(Codec codec, Class<?> clazz, int id) {
            super();
            this.codec = codec;
            this.clazz = clazz;
            this.id = id;
        }

        public Codec getCodec() {
            return codec;
        }

        public void setCodec(Codec codec) {
            this.codec = codec;
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
}
