package org.forfun.mmorpg.rpc.data;

import org.forfun.mmorpg.protocol.codec.impl.protostuff.ProtostuffCodecUtil;

public class RpcDataPackage {

    private int type;

    private byte[] data;

    public static RpcDataPackage newResponse(RpcResponseData response) {
        RpcDataPackage data = new RpcDataPackage();
        data.setType(1);
        data.setData(ProtostuffCodecUtil.serializer(response));

        return data;
    }

    public static RpcDataPackage newRequest(RpcRequestData request) {
        RpcDataPackage data = new RpcDataPackage();
        data.setType(0);
        data.setData(ProtostuffCodecUtil.serializer(request));

        return data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
