package org.forfun.mmorpg.rpc.client;

public class RpcClientOptions {

    private String ipAddr;

    private int port;

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
