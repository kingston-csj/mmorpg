package org.forfun.mmorpg.protocol.codec.impl.reflect;

public class SerializerMeta {

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
